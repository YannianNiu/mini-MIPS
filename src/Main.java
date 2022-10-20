import java.io.*;

public class Main {


    public static void main(String[] args) throws IOException {
        Instruction instruction = new Instruction();
        Memory memory = new Memory();

        int base_address = 64, address = 64,flag=0,break_position=0;
        String tempString,originalString,originalString2;
        FileReader fileReader = new FileReader("sample.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        FileWriter fileWriter = new FileWriter("disassembly.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        FileWriter fileWriter2 = new FileWriter("simulation.txt");
        PrintWriter printWriter2 = new PrintWriter(fileWriter2);
        while ((tempString=bufferedReader.readLine())!=null) {
            tempString = tempString.substring(0,32);
            originalString = tempString;
            if(flag==0) {
                instruction = Method.transform(tempString);
                if (instruction.Function.equals("BREAK")) {
                    flag = 1;
                    break_position = address;
                }
                tempString = Instruction.writeIn(instruction, address);
                StringBuilder stringBuilder = new StringBuilder(originalString);
                stringBuilder.insert(6,' ');
                stringBuilder.insert(12,' ');
                stringBuilder.insert(18,' ');
                stringBuilder.insert(24,' ');
                stringBuilder.insert(30,' ');
                originalString2 = stringBuilder.toString();
                printWriter.println(originalString2+tempString);
                //System.out.println(originalString+tempString);
            }
            else{
                tempString = String.valueOf(Method.binary_complement(tempString));
                tempString = Instruction.writeInNum(tempString, address);
                printWriter.println(originalString+tempString);
                //System.out.println(originalString);
            }
            memory.push_vector(originalString);
            address=address+4;
        }
        printWriter.flush();
        printWriter.close();


        //break_position = (address - base_address)/4 + 1;
        CPU cpu = new CPU(memory,break_position);
        //memory.println_data(memory.getVector());
        cpu.calculation(printWriter2);
        printWriter2.flush();
        printWriter2.close();
    }
}
