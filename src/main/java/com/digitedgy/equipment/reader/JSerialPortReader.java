package com.digitedgy.equipment.reader;

import com.digitedgy.util.ConfigReader;
import com.fazecast.jSerialComm.SerialPort;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Enumeration;

public class JSerialPortReader {

    static String configuredPort = ConfigReader.get().getEquipment().getPort();
    static int configuredRate = ConfigReader.get().getEquipment().getRate();

    public static String read() {
        String lastEntry = "ERR";
        try {
            SerialPort[] ports = SerialPort.getCommPorts();
            SerialPort actualPort = null;

            for (SerialPort port : ports) {
                if (configuredPort.equals(port.getSystemPortName())) {
                    actualPort = port;
                    break;
                }
            }
            if (actualPort == null) {
                throw new Exception("Could not find the configured Serial Port");
            }

            actualPort.setComPortParameters(configuredRate, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
            actualPort.openPort();

            try {
                if(actualPort.bytesAvailable()==0)
                    throw new RuntimeException("No data available from the Equipment!");
                byte[] readBuffer = new byte[actualPort.bytesAvailable()];
                int numRead = actualPort.readBytes(readBuffer, readBuffer.length);
                String output = new String(readBuffer);
                System.out.println("Output from the machine : " + output);
                lastEntry = output.split("\\n")[output.split("\\n").length - 1];
                System.out.println("Considering the last entry : " + lastEntry);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                actualPort.closePort();
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return lastEntry;
    }
}
