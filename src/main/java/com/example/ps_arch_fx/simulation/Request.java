package com.example.ps_arch_fx.simulation;


public class Request {   //внутренний класс для заявки
    private boolean inBuffer = false;

    //считаем, что все заявки сначала попадают в буфер, а потом на прибор
    private boolean inConsumer = false;  //находится ли заявка на приборе
    private boolean inRefusal = false; //ушла ли заявка в отказ
    private double generationTime; //время генерации заявки
    private int number;
    private final int producerNumber;

    public Request(double generationTime, int number, int producerNumber) {
        this.generationTime = generationTime;
        this.number = number;
        this.producerNumber = producerNumber;
    }

    public int getProducerNumber() {
        return producerNumber;
    }

    public boolean isInBuffer() {
        return inBuffer;
    }

    public void setInBuffer(boolean inBuffer) {
        this.inBuffer = inBuffer;
    }

    public boolean isInConsumer() {
        return inConsumer;
    }

    public void setInConsumer(boolean inConsumer) {
        this.inConsumer = inConsumer;
    }

    public boolean isInRefusal() {
        return inRefusal;
    }

    public void setInRefusal(boolean inRefusal) {
        this.inRefusal = inRefusal;
    }

    public double getGenerationTime() {
        return generationTime;
    }

    public void setGenerationTime(double generationTime) {
        this.generationTime = generationTime;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}