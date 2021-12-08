package com.example.ps_arch_fx.simulation;

import com.example.ps_arch_fx.data.SharedSimulationData;

import java.util.List;

//Выбор заявки из буфера на прибор по номеру прибора
public record ConsumptionManager(SharedSimulationData simulationData) {
    public void putToProcessing(List<Buffer> buffers, List<Consumer> consumers, List<Request> requests, List<Producer> producers) {
        boolean hasEmptyConsumer = false; //Проверяем, есть ли свободный прибор
        int emptyConsumerIndex = 0;

        for (int i = 0; i < consumers.size(); i++) {
            if (consumers.get(i).isEmpty()) {
                hasEmptyConsumer = true;       //Если нашли свободный, меняем флаг
                emptyConsumerIndex = i;
                break;
            }
        }

        //Если нашли свободный прибор, то кидаем туда выбранную из буфера заявку, если свободных приборов нет, то
        // ничего не делаем
        if (hasEmptyConsumer) {
            double maxStoringTime = 0;

            //Проверяем есть ли у нас заявки в буферах, если есть, то кидаем их на приборы, если нет, то ничего не
            // делаем
            boolean isBuffersEmpty = true; //Если все буферы пустые
            for (Buffer buffer : buffers) {
                if (!buffer.isEmpty()) {
                    isBuffersEmpty = false;
                    maxStoringTime = simulationData.getSimulationTime() - buffer.getTimeAdd();
                    break;
                }
            }

            if (!isBuffersEmpty) {
                Request req = null;
                int bufferNumber = 0;   //индекс буфера, из которого забрали заявку (далее сдвигаем все заявки влево)
                int producerNumber = 0;

                //Находим заявку, простоявшую в буфере дольше всех
                for (int i = 0; i < buffers.size(); i++) {
                    if (!buffers.get(i).isEmpty() && simulationData.getSimulationTime() - buffers.get(i).getTimeAdd() >= maxStoringTime) {

                        maxStoringTime = simulationData.getSimulationTime() - buffers.get(i).getTimeAdd();
                        req = buffers.get(i).getRequest();
                        bufferNumber = i;
                        producerNumber = req.getProducerNumber();
                    }
                }

                buffers.get(bufferNumber).delete();     //удаляем найденную заявку из буфера
                //Добавляем информацию о нахождении в буфере заявки из нужного источника
                producers.get(producerNumber - 1).setRequestsStoringTime(buffers.get(bufferNumber).getRequestStoringTime());

                simulationData.setSimulationTime(buffers.get(bufferNumber).getTimeOut()); //Фиксируем время удаления

                //Если нашли заявку, т.е. буферы НЕ пустые, то отправляем в свободный прибор на обработку
                consumers.get(emptyConsumerIndex).add(req);

                //Фиксируем время поступления заявки на прибор
                simulationData.setSimulationTime(consumers.get(emptyConsumerIndex).getTimeAdd());
            }
        }

        //Проверяем, есть ли прибор, который закончит обработку заявки раньше,
        //чем сгенерировалась самая ранняя из заявок, находящихся в листе
        Consumer consumer = consumers.get(0);
        for (Consumer c : consumers) {
            if (!c.isEmpty()) {
                consumer = c;
                break;
            }
        }

        double minTimeToTreatment = consumer.getTimeToTreatment();
        int consumerNumber = consumer.getNumber();
        for (Consumer c : consumers) {
            if (c.getTimeToTreatment() < minTimeToTreatment && !c.isEmpty()) {
                minTimeToTreatment = c.getTimeToTreatment();
                consumerNumber = c.getNumber();
            }
        }

        double minGenerationTime = requests.get(0).getGenerationTime();
        for (Request req : requests) {
            if (req.getGenerationTime() < minGenerationTime) {
                minGenerationTime = req.getGenerationTime();
            }
        }

        //Если нашли такой прибор, то фиксируем системное время и удаляем заявку из прибора
        if (minTimeToTreatment < minGenerationTime) {
            simulationData.setSimulationTime(minTimeToTreatment); //Фиксируем событие - Завершение обработки и удаление заявки из прибора.

            int producerNumber = consumer.getRequest().getProducerNumber();
            consumers.get(consumerNumber - 1).delete();
            producers.get(producerNumber - 1).setRequestsProcessingTime(consumers.get(consumerNumber - 1).getRequestProcessingTime());
        }

        if (simulationData.isGenerateIsReady()) {
            boolean isAllConsumersEmpty = false;
            int count = 0;
            for (Consumer c : consumers) {
                if (c.isEmpty()) {
                    count++;
                }
            }
            if (count == consumers.size()) {
                isAllConsumersEmpty = true;
            }

            if (!isAllConsumersEmpty) {
                simulationData.setSimulationTime(minTimeToTreatment); //Фиксируем событие - Завершение обработки и удаление заявки из прибора.

                int producerNumber = consumer.getRequest().getProducerNumber();
                consumers.get(consumerNumber - 1).delete();
                producers.get(producerNumber - 1).setRequestsProcessingTime(consumers.get(consumerNumber - 1).getRequestProcessingTime());
            }
        }
    }
}