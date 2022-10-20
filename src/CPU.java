import javax.swing.*;
import java.io.PrintWriter;
import java.util.Vector;

public class CPU {

    private Memory memory = new Memory();
    private Instruction instruction = new Instruction();
    private int break_position;
    private Vector<Integer> registers = new Vector<Integer>();
    private int PC = 64;
    private int base_PC=64;

    public void InitalRegister(){
        for(int i=0;i<32;i++){
            registers.add(0);
        }
    }


    CPU(Memory memory,int break_position){
        this.memory = memory;
        this.break_position=break_position;
    }

    public Vector<Integer> push_registers(int vaule){
        this.registers.add(vaule);
        return this.registers;
    }

    public Vector<Integer> getRegisters() {
        return this.registers;
    }

    public void calculation(PrintWriter printWriter){
        int cycle_num=1;
        int data_num,count_data=0;
        InitalRegister();
        instruction = Method.transform(this.memory.getVector((PC-base_PC)/4));
        while (PC<=break_position&&!instruction.Function.equals("BREAK")) {
            instruction = Method.transform(this.memory.getVector((PC-base_PC)/4));

            printWriter.println("--------------------");
            //System.out.println("--------------------");
            printWriter.println("Cycle:"+cycle_num+Instruction.writeIn2(instruction,PC));
            //System.out.println("cycle: "+cycle_num +Instruction.writeIn(instruction,PC));
            printWriter.print("\n");
            //System.out.print("\n");
            //printWriter.println("Registers");
            PC = calculation_details(instruction);
            //System.out.print("Registers"+"\n"+"R00:\t");
            printWriter.print("Registers"+"\n"+"R00:\t");
            for(int i=0;i<32;i++) {
                if(i==16){
                    //System.out.print("\n"+"R16:\t");
                    printWriter.print("\n"+"R16:\t");
                }

                if(i==15||i==31)
                    printWriter.print(registers.get(i));
                else
                    printWriter.print(registers.get(i)+"\t");
                //System.out.print(registers.get(i)+" ");
            }
            //System.out.println("\n");
            printWriter.println("\n");

            count_data=0;
            //输出data
            //System.out.print("Data");
            printWriter.print("Data");
            for(int i=(break_position-base_PC+4)/4;i<memory.getVectorSize();i++){
                if(count_data%8==0){
                    //System.out.print("\n");
                    printWriter.print("\n");
                    //System.out.print(4*i+base_PC+":\t");
                    printWriter.print(4*i+base_PC+":\t");
                }
                count_data++;
                data_num = Method.binary_complement(memory.getVector(i));
                if(count_data%8==0)
                    printWriter.print(data_num);
                else
                    printWriter.print(data_num+"\t");
                //System.out.print(data_num+" ");
            }
            //System.out.println("\n");
            printWriter.println("\n");
            cycle_num++;
        }
    }

    public int calculation_details(Instruction instruction){
        int rs_num,rt_num,rd_num,immediate,base,temp;
        switch (instruction.Function){
            case("ADD"):
                if(instruction.printType.equals("1")){
                    rs_num = registers.get(Integer.parseInt(instruction.getRs()));
                    rt_num = registers.get(Integer.parseInt(instruction.getRt()));
                    registers.set(Integer.parseInt(instruction.getRd()),rs_num+rt_num);
                }
                else if(instruction.printType.equals("2")){
                    rs_num = registers.get(Integer.parseInt(instruction.getRs()));
                    immediate = Integer.parseInt(instruction.getImmediate());
                    registers.set(Integer.parseInt(instruction.getRt()),rs_num+immediate);
                }
                PC+=4;
                break;
            case("SUB"):
                if(instruction.printType.equals("1")) {
                    rs_num = registers.get(Integer.parseInt(instruction.getRs()));
                    rt_num = registers.get(Integer.parseInt(instruction.getRt()));
                    registers.set(Integer.parseInt(instruction.getRd()), rs_num - rt_num);
                }
                else if(instruction.printType.equals("2")){
                    rs_num = registers.get(Integer.parseInt(instruction.getRs()));
                    immediate = Integer.parseInt(instruction.getImmediate());
                    registers.set(Integer.parseInt(instruction.getRt()),rs_num-immediate);
                }
                PC+=4;
                break;
            case("BEQ"):
                rs_num = registers.get(Integer.parseInt(instruction.getRs()));
                rt_num = registers.get(Integer.parseInt(instruction.getRt()));
                if(rs_num == rt_num)
                    PC = Integer.parseInt(instruction.getImmediate())+PC+4;
                else
                    PC+=4;
                break;
            case("SLL"):
                rt_num = registers.get(Integer.parseInt(instruction.getRt()));
                immediate = Integer.parseInt(instruction.getImmediate());
                rt_num = rt_num<<immediate;
                registers.set(Integer.parseInt(instruction.getRd()),rt_num);
                PC+=4;
                break;
            case("LW"):   // rt ← memory[base+offset]
                immediate = Integer.parseInt(instruction.getImmediate());
                base = registers.get(Integer.parseInt(instruction.getBase()));
                temp=Method.binary_complement(memory.getVector((immediate+base-base_PC)/4));
                registers.set(Integer.parseInt(instruction.getRt()), temp);
                PC+=4;
                break;
            case ("SW"):   //Memory[base+offset] <- rt
                rt_num = registers.get(Integer.parseInt(instruction.getRt()));

                immediate = Integer.parseInt(instruction.getImmediate());      //offset的值 188
                base = registers.get(Integer.parseInt(instruction.getBase())); //R16寄存器里的值 0

                memory.modifyVector((base+immediate-base_PC)/4,Method.Complement2Binary(rt_num));
                PC+=4;
                break;
            case("SRA"):
                immediate = Integer.parseInt(instruction.getImmediate());
                rt_num = registers.get(Integer.parseInt(instruction.getRt()));
                registers.set(Integer.parseInt(instruction.getRd()),rt_num>>immediate);
                PC+=4;
                break;
            case ("SRL"):
                rt_num = registers.get(Integer.parseInt(instruction.getRt()));
                immediate = Integer.parseInt(instruction.getImmediate());
                rt_num = rt_num>>immediate;
                registers.set(Integer.parseInt(instruction.getRt()),rt_num);
                PC+=4;
                break;
            case ("J"):
                immediate = Integer.parseInt(instruction.getImmediate());
                PC = immediate;
                break;
            case ("JR"):
                rs_num = registers.get(Integer.parseInt(instruction.getRs()));
                PC = rs_num;
                break;
            case ("MUL"):
                rs_num = registers.get(Integer.parseInt(instruction.getRs()));
                rt_num = registers.get(Integer.parseInt(instruction.getRt()));
                registers.set(Integer.parseInt(instruction.getRd()),rs_num*rt_num);
                PC+=4;
                break;
            case ("BGTZ"):
                rs_num = registers.get(Integer.parseInt(instruction.getRs()));
                if(rs_num>0)
                    PC = (Integer.parseInt(instruction.getImmediate())-2)*4+64;
                else
                    PC+=4;
                break;
            case ("BLTZ"):
                rs_num = registers.get(Integer.parseInt(instruction.getRs()));
                if(rs_num<0)
                    PC = (Integer.parseInt(instruction.getImmediate())-2)*4+64;
                else
                    PC+=4;
                break;
            case ("NOP"):
                PC+=4;
                break;
            case("AND"):
                rs_num = registers.get(Integer.parseInt(instruction.getRs()));
                rt_num = registers.get(Integer.parseInt(instruction.getRt()));
                registers.set(Integer.parseInt(instruction.getRd()),rs_num&rt_num);
                PC+=4;
                break;
            case ("NOR"):
                rs_num = registers.get(Integer.parseInt(instruction.getRs()));
                rt_num = registers.get(Integer.parseInt(instruction.getRt()));
                registers.set(Integer.parseInt(instruction.getRd()),rs_num|rt_num);
                PC+=4;
                break;
            case ("SLT"):
                rs_num = registers.get(Integer.parseInt(instruction.getRs()));
                rt_num = registers.get(Integer.parseInt(instruction.getRt()));
                registers.set(Integer.parseInt(instruction.getRd()),rs_num<<rt_num);
                PC+=4;
                break;
            case ("BREAK"):
                PC+=4;
                break;
        }
        return PC;
    }
}
