package com.example.ps_arch_fx.simulation;

import com.example.ps_arch_fx.data.SharedDataHolder;
import com.example.ps_arch_fx.data.SharedSimulationData;
import com.example.ps_arch_fx.data.SimulationResults;

import java.util.ArrayList;
import java.util.List;

public class Simulator {
    private final SharedDataHolder dataHolder;
    private SharedSimulationData simulationData;
    private List<Producer> producers;
    private List<Buffer> buffers;
    private List<Consumer> consumers;
    private List<Request> requests;
    private ConsumptionManager consumptionManager;
    private int bufferPointer;

    public Simulator(SharedDataHolder dataHolder) {
        this.dataHolder = dataHolder;
    }

    public void simulate() {
        initSimulation();

        //Главный цикл программы
        while (simulationData.getTotalGeneratedRequestsNumber() != simulationData.getRequestsNumber()) {
            //Находим заявку с минимальным временем генерации и берём её из массива заявок
            //На место этой заявки встанет новая заявка из этого же источника

            double minGenerationTime = requests.get(0).getGenerationTime();
            Request newRequest = requests.get(0); //Новая заявка, с которой работаем
            int producerNumber = requests.get(0).getProducerNumber(); // номер источника, у которого сгенерировалась заявка
            int position = 0;

            for (int i = 0; i < requests.size(); i++) {
                if (requests.get(i).getGenerationTime() < minGenerationTime) {
                    minGenerationTime = requests.get(i).getGenerationTime();
                    newRequest = requests.get(i);
                    producerNumber = requests.get(i).getProducerNumber();
                    position = i;
                }
            }

            simulationData.setSimulationTime(newRequest.getGenerationTime()); //Системное время фиксирует событие - Генерация заявки

            requests.remove(position); //Удаляем самую раннюю сгенерированную заявку
            requests.add(producers.get(producerNumber - 1).generate()); //Добавляем новую заявку из того же источника

            int i = bufferPointer;
            do { //Находим пустой буфер
                if (i >= buffers.size()) { // переходим в начало списка буферов, по кольцу
                    i = 0;
                }

                if (buffers.get(i).isEmpty()) { //Если нашли, то добавляем туда новую заявку
                    buffers.get(i).add(newRequest);

                    moveBufferPointer(i + 1); // сдвигаем указатель на позицию,
                    // следующую за вставленной заявкой

                    simulationData.setSimulationTime(buffers.get(i).getTimeAdd()); //Системное время фиксирует событие - Добавление в буфер
                    break;
                }

                i++;
            } while (i + 1 < bufferPointer);

            if (!newRequest.isInBuffer()) { //Если заявка не была добавлена в буфер (все заняты)
                Request req = buffers.get(bufferPointer).getRequest(); //Достаём заявку под указателем

                int reqProducerNumber = req.getProducerNumber(); //и номер её источника

                req.setInRefusal(true); //Ставим статус "В отказ"
                producers.get(reqProducerNumber - 1).incrementCountRefusal();

                //Увеличиваем счётчик заявок в отказе
                simulationData.setTotalRefusedRequestsNumber(simulationData.getTotalRefusedRequestsNumber() + 1);

                //Удаляем заявку под указателем из буфера
                buffers.get(bufferPointer).delete();

                //Добавляем информацию о времени нахождения в буфере заявки из нужного источника
                producers.get(reqProducerNumber - 1).setRequestsStoringTime(buffers.get(bufferPointer).getRequestStoringTime());

                //Системное время фиксирует событие - Уход заявки в отказ
                simulationData.setSimulationTime(buffers.get(bufferPointer).getTimeOut());

                //Ставим на её место новую заявку
                buffers.get(bufferPointer).add(newRequest);

                //Системное время фиксирует событие - Добавление в буфер
                simulationData.setSimulationTime(buffers.get(bufferPointer).getTimeAdd());

                // сдвигаем указатель
                moveBufferPointer();
            }

            //внутри происходит постановка заявки на прибор из буфера, если есть свободный прибор
            consumptionManager.putToProcessing(buffers, consumers, requests, producers);
        }

        // заполняем результаты симуляции
        for (Producer producer : producers) {
            double avgWaitingTime = producer.getRequestsProcessingTime() / producer.getRequestsNumber();
            double avgProcessingTime = producer.getRequestsStoringTime() / producer.getRequestsNumber();

            int idx = producer.getNumber() - 1;

            dataHolder.getResults().getAutoModeData().getProducersData().get(idx).getRequestsNumber().setValue(producers.get(idx).getRequestsNumber());
            dataHolder.getResults().getAutoModeData().getProducersData().get(idx).getKnockoutProbability().setValue(
                    (double) producer.getRefusedRequestNumber() / producers.get(idx).getRequestsNumber()
            );
            dataHolder.getResults().getAutoModeData().getProducersData().get(idx).getAvgSystemTime().setValue(avgWaitingTime + avgProcessingTime);
            dataHolder.getResults().getAutoModeData().getProducersData().get(idx).getAvgWaitingTime().setValue(avgWaitingTime);
            dataHolder.getResults().getAutoModeData().getProducersData().get(idx).getAvgProcessingTime().setValue(avgProcessingTime);
        }

        for (Consumer consumer : consumers) {
            dataHolder.getResults().getAutoModeData().getConsumersData().get(consumer.getNumber() - 1).getUsageCoefficient().setValue(
                    consumer.getTimeInDevice() / simulationData.getSimulationTime()
            );
        }

        // сбрасываем значения в буферах, источниках и приборах
        buffers.forEach(Buffer::clear);
        consumers.forEach(Consumer::clear);
        producers.forEach(Producer::clear);
    }

    private void moveBufferPointer() {
        bufferPointer = bufferPointer + 1 >= buffers.size() ? 0 : bufferPointer + 1;
    }

    private void moveBufferPointer(int newValue) {
        bufferPointer = newValue >= buffers.size() ? 0 : newValue;
    }

    private void initSimulation() {
        simulationData = new SharedSimulationData();
        bufferPointer = 0;

        simulationData.setProducersNumber(dataHolder.getParameters().getProducersNumber());
        simulationData.setBuffersNumber(dataHolder.getParameters().getBufferSize());
        simulationData.setConsumersNumber(dataHolder.getParameters().getConsumersNumber());
        simulationData.setRequestsNumber(dataHolder.getParameters().getRequestsNumber());
        simulationData.setAlpha(dataHolder.getParameters().getAlpha());
        simulationData.setBeta(dataHolder.getParameters().getBeta());
        simulationData.setLambda(dataHolder.getParameters().getLambda());

        for (int i = 0; i < simulationData.getConsumersNumber(); i++) {
            dataHolder.getResults().getAutoModeData().getConsumersData().add(new SimulationResults.AutoModeStatistics.ConsumerRow());
        }
        for (int i = 0; i < simulationData.getProducersNumber(); i++) {
            dataHolder.getResults().getAutoModeData().getProducersData().add(new SimulationResults.AutoModeStatistics.ProducerRow());
        }

        producers = new ArrayList<>();
        for (int i = 0; i < simulationData.getProducersNumber(); i++) {
            producers.add(new Producer(i + 1, simulationData));
        }

        buffers = new ArrayList<>();
        for (int i = 0; i < simulationData.getBuffersNumber(); i++) {
            buffers.add(new Buffer(i + 1, simulationData, dataHolder.getResults()));
        }

        consumers = new ArrayList<>();
        for (int i = 0; i < simulationData.getConsumersNumber(); i++) {
            consumers.add(new Consumer(i + 1, simulationData, dataHolder.getResults()));
        }

        requests = new ArrayList<>(); // генерируем по одной заявке из каждого источника
        for (Producer s : producers) {
            requests.add(s.generate());
        }

        simulationData.setGenerateIsReady(false);

        consumptionManager = new ConsumptionManager(simulationData); //Менеджер занимается постановкой заявок на прибор из буферов
    }
}