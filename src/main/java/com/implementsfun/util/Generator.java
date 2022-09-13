package com.implementsfun.util;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

import com.google.protobuf.Timestamp;
import com.implementsfun.protoj.LaptopMessage.*;
import com.implementsfun.protoj.ProcessorMessage.*;
import com.implementsfun.protoj.KeyboardMessage.*;
import com.implementsfun.protoj.ScreenMessage.*;
import com.implementsfun.protoj.MemoryMessage.*;
import com.implementsfun.protoj.StorageMessage.*;

public class Generator {
    private final Random rand;

    public Generator(){
        rand=new Random();
    }

    /**
     * 外滩星巴克的人越来越多，人说话越来越本地化
     * 下午3点去拿毕业证
     * 中午吃点东西
     * 有点想喝橙汁
     * @return random int
     */
    public double initLaptopScore(){
        return randomInt(1,10);
    }

    public CPU initCPU(){
        String brand = randomCPUBrand();
        String name = randomCPUName(brand);
        int numberCores = randomInt(2,8);
        int numberThreads = randomInt(numberCores,12);
        double minGhz = randomDouble(2.0,3.5);
        double maxGhz = randomDouble(minGhz,3.5);
        return CPU.newBuilder()
                .setBrand(brand)
                .setName(name)
                .setNumberCores(numberCores)
                .setNumberThreads(numberThreads)
                .setMinGhz(minGhz)
                .setMaxGhz(maxGhz)
                .build();
    }
    public Laptop initLaptop(){
        String brand=randomLaptopBrand();
        String name = randomLaptopName(brand);
        double weightKg=randomDouble(1.0,3.0);
        double priceUsd=randomDouble(1500,3500);
        int releaseYear = randomInt(2015,2022);
        return Laptop.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setBrand(brand)
                .setName(name)
                .setCpu(initCPU())
                .setRam(initRam())
                .addGpus(initGPU())
                .addStorages(initSSD())
                .addStorages(initHDD())
                .setScreen(initScreen())
                .setKeyboard(initKeyboard())
                .setWeightKg(weightKg)
                .setPriceUsd(priceUsd)
                .setReleaseYear(releaseYear)
                .setUpdatedAt(timestampNow())
                .build();
    }

    private Timestamp timestampNow() {
        Instant now = Instant.now();
        return Timestamp.newBuilder()
                .setSeconds(now.getEpochSecond())
                .setNanos(now.getNano())
                .build();
    }

    private Keyboard initKeyboard() {
        return Keyboard.newBuilder()
                .setLayout(randomKeyboardLayout())
                .setBacklit(rand.nextBoolean())
                .build();
    }

    private Keyboard.Layout randomKeyboardLayout() {
        switch (rand.nextInt(3)){
            case 1:
                return Keyboard.Layout.QWERTY;
            case 2:
                return Keyboard.Layout.QWERTZ;
            default:
                return Keyboard.Layout.AZERTY;
        }
    }

    private Screen initScreen() {
        int height = randomInt(1080,4320);
        int width = height * 16 / 9;
        Screen.Resolution resolution = Screen.Resolution.newBuilder()
                .setHeight(height)
                .setWidth(width)
                .build();
        return Screen.newBuilder()
                .setSizeInch(randomFloat(13,17))
                .setResolution(resolution)
                .setPanel(randomScreenPanel())
                .setMultitouch(rand.nextBoolean())
                .build();
    }

    private Screen.Panel randomScreenPanel() {
        return rand.nextBoolean()?Screen.Panel.IPS:Screen.Panel.OLED;
    }

    private float randomFloat(float min, float max) {
        return min+rand.nextFloat()*(max-min);
    }

    private Storage initHDD() {
        Memory memory = Memory.newBuilder()
                .setValue(randomInt(1,6))
                .setUnit(Memory.Unit.TERABYTE).build();
        return Storage.newBuilder()
                .setDriver(Storage.Driver.HDD)
                .setMemory(memory)
                .build();
    }

    private Storage initSSD() {
        Memory memory = Memory.newBuilder()
                .setValue(randomInt(128,1024))
                .setUnit(Memory.Unit.GIGABYTE).build();
        return Storage.newBuilder()
                .setDriver(Storage.Driver.SSD)
                .setMemory(memory)
                .build();
    }

    private GPU initGPU() {
        String brand=randomGpuBrand();
        String name = randomGPUName(brand);
        double minGhz = randomDouble(1.0,1.5);
        double maxGhz = randomDouble(minGhz,2.0);
        Memory memory = Memory.newBuilder()
                .setValue(randomInt(2,6))
                .setUnit(Memory.Unit.GIGABYTE)
                .build();
        return GPU.newBuilder()
                .setBrand(brand)
                .setName(name)
                .setMinGhz(minGhz)
                .setMaxGhz(maxGhz)
                .setMemory(memory)
                .build();
    }

    private String randomGPUName(String brand) {
        if("NVIDIA".equals(brand)){
            return randomStringFromSet("RTX 2060","RTX 2070","GTX 1660-Ti");
        }
        return randomStringFromSet("RX 590","RX 580","RX 5700-XT");
    }

    private String randomGpuBrand() {
        return randomStringFromSet("NVIDIA","AMD");
    }

    private Memory initRam() {
        return Memory.newBuilder()
                .setValue(randomInt(4,64))
                .setUnit(Memory.Unit.GIGABYTE)
                .build();
    }

    private String randomLaptopName(String brand) {
        switch (brand){//ngsysgrz
            case "Apple":
                return randomStringFromSet("Macbook Air","Macbook Pro");
            case "Dell":
                return randomStringFromSet("Latitude","Alienware","XPS");
            case "Huawei":
                return randomStringFromSet("Notebook D","Notebook E");
            default: return "Lucky Laptop";
        }

    }

    private String randomLaptopBrand() {
        return randomStringFromSet("Apple","Dell","Huawei");
    }

    private String randomStringFromSet(String ...eles) {
        int n = eles.length;
        return n==0?"":eles[rand.nextInt(n)];
    }

    private double randomDouble(double min, double max) {
        return min+rand.nextDouble()*(max-min);
    }

    private int randomInt(int min, int max) {
        return min+rand.nextInt(max-min+1);
    }

    private String randomCPUName(String brand) {
        if ("Intel".equals(brand)){
            return  randomStringFromSet("Xeon E-2286M","Core i9-9989HK","Core i7-9750H");
        }
        return randomStringFromSet("Ryzen 7 PRO 2700U","Ryzen 5 PRO 3500U");

    }

    private String randomCPUBrand() {
        return randomStringFromSet("Intel","AMD");
    }
}
