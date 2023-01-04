package com.digitedgy;

import com.digitedgy.equipment.reader.JSerialPortReader;
import com.digitedgy.model.Config;
import com.digitedgy.model.Equipment;
import com.digitedgy.poller.DBStore;
import com.digitedgy.transform.Transformable;
import com.digitedgy.util.ConfigReader;
import com.fazecast.jSerialComm.SerialPort;

public class MachineDataReader {
    public static void main(String[] args) {
        try {
            Config config = ConfigReader.loadConfig(args[0]);
            SerialPort[] ports = SerialPort.getCommPorts();
            System.out.println("Available serial ports on the system");
            for(SerialPort port:ports) {
                System.out.println(port.getSystemPortName());
            }

            int interval = config.getPoll().getInterval();
            DBStore dbStore = null;
            Equipment configuredEquipment = config.getEquipment();
            while(true) {
                dbStore = DBStore.getInstance();
                if(dbStore.poll(configuredEquipment)) {
                    String data = JSerialPortReader.read();
                    System.out.println("Data received from the Equipment : "+data);
                    String transformedData = data;
                    if(data!="ERR" && configuredEquipment.getData()!=null && !configuredEquipment.getData().getTransform().isEmpty()) {
                        Transformable transformable = (Transformable) Class.forName(configuredEquipment.getData().getTransform()).getConstructor().newInstance();
                        transformedData = transformable.transform(data);
                    }
                    dbStore.pushToStore(configuredEquipment,transformedData);
                }
                Thread.sleep(interval*1000);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
