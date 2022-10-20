import java.sql.SQLOutput;

public class Instruction {

    public String imm;
    public String Opcode;
    public String rs;
    public String rt;
    public String rd;
    public String Function;
    public String Immediate;
    public String printType;

    public String getBase() {
        return base;
    }

    public String base;

    public void setRd(String rd) {
        this.rd = rd;
    }

    public void setFunction(String function) {
        Function = function;
    }

    public void setImmediate(String immediate) {
        Immediate = immediate;
    }

    public void setImm(String imm) {
        this.imm = imm;
    }

    public void setOpcode(String opcode) {
        Opcode = opcode;
    }

    public void setRs(String rs) {
        this.rs = rs;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public String getImm() {
        return imm;
    }

    public String getOpcode() {
        return Opcode;
    }

    public String getRt() {
        return rt;
    }

    public String getRd() {
        return rd;
    }

    public String getFunction() {
        return Function;
    }

    public String getImmediate() {
        return this.Immediate;
    }

    public String getRs() {
        return rs;
    }

    public static String writeIn2(Instruction instruction,int address){
        String string = null;
        if(instruction.printType.equals("1")){
            string = "\t"+ String.valueOf(address) + "\t"+ instruction.Function +"\tR"+instruction.rd+", R"+instruction.rs+", R"+instruction.rt;
        }
        if(instruction.printType.equals("2")){
            string = "\t"+ String.valueOf(address) + "\t"+ instruction.Function +"\tR"+instruction.rt+", R"+instruction.rs+", #"+instruction.Immediate;
        }
        if (instruction.printType.equals("LSW")){
            string = "\t"+ String.valueOf(address) + "\t"+ instruction.Function +"\tR"+instruction.rt+ ", "+instruction.Immediate+"(R"+instruction.base+")";
        }
        if (instruction.printType.equals("BZ")){
            string = "\t"+ String.valueOf(address) + "\t"+ instruction.Function +"\tR"+instruction.rs+ ", #"+instruction.Immediate;
        }
        if (instruction.printType.equals("BEQ")){
            string = "\t"+ String.valueOf(address) + "\t"+ instruction.Function +"\tR"+instruction.rs+ ", R"+instruction.rt +", #"+instruction.Immediate;
        }
        if (instruction.printType.equals("J")){
            string = "\t"+ String.valueOf(address) + "\t"+ instruction.Function +"\t#"+instruction.Immediate;
        }
        if (instruction.printType.equals("JR")){
            string = "\t"+ String.valueOf(address) + "\t"+ instruction.Function +"\tR"+instruction.rs;
        }
        if (instruction.printType.equals("AL_SLL")){
            string = "\t"+ String.valueOf(address) + "\t"+ instruction.Function +"\tR"+instruction.rd+ ", R"+instruction.rt+ ", #"+instruction.Immediate;
        }
        if (instruction.printType.equals("BREAK")){
            string = "\t"+ String.valueOf(address) + "\t"+ instruction.Function;
        }
        if(instruction.printType.equals("NOP")){
            string = "\t"+ String.valueOf(address) + "\t"+ instruction.Function;
        }
        return string;

    }

    public static String writeIn(Instruction instruction,int address){
        String string = null;
        if(instruction.printType.equals("1")){
            string = "\t"+ String.valueOf(address) + "\t"+ instruction.Function +" R"+instruction.rd+", R"+instruction.rs+", R"+instruction.rt;
        }
        if(instruction.printType.equals("2")){
            string = "\t"+ String.valueOf(address) + "\t"+ instruction.Function +" R"+instruction.rt+", R"+instruction.rs+", #"+instruction.Immediate;
        }
        if (instruction.printType.equals("LSW")){
            string = "\t"+ String.valueOf(address) + "\t"+ instruction.Function +" R"+instruction.rt+ ", "+instruction.Immediate+"(R"+instruction.base+")";
        }
        if (instruction.printType.equals("BZ")){
            string = "\t"+ String.valueOf(address) + "\t"+ instruction.Function +" R"+instruction.rs+ ", #"+instruction.Immediate;
        }
        if (instruction.printType.equals("BEQ")){
            string = "\t"+ String.valueOf(address) + "\t"+ instruction.Function +" R"+instruction.rs+ ", R"+instruction.rt +", #"+instruction.Immediate;
        }
        if (instruction.printType.equals("J")){
            string = "\t"+ String.valueOf(address) + "\t"+ instruction.Function +" #"+instruction.Immediate;
        }
        if (instruction.printType.equals("JR")){
            string = "\t"+ String.valueOf(address) + "\t"+ instruction.Function +" R"+instruction.rs;
        }
        if (instruction.printType.equals("AL_SLL")){
            string = "\t"+ String.valueOf(address) + "\t"+ instruction.Function +" R"+instruction.rd+ ", R"+instruction.rt+ ", #"+instruction.Immediate;
        }
        if (instruction.printType.equals("BREAK")){
            string = "\t"+ String.valueOf(address) + "\t"+ instruction.Function;
        }
        if(instruction.printType.equals("NOP")){
            string = "\t"+ String.valueOf(address) + "\t"+ instruction.Function;
        }
        return string;

    }

    public static String writeInNum(String string,int address){
        String str  = "\t"+ String.valueOf(address) + "\t"+ string;
        return str;
    }
}
