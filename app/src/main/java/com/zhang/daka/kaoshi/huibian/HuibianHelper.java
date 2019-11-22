package com.zhang.daka.kaoshi.huibian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhangyuncai on 2019/11/22.
 */
public class HuibianHelper {
    public static List<HuibianModel> getHuibianModels() {
        List<HuibianModel> list = new ArrayList<>();

        HuibianModel model = null;

        model = new HuibianModel();
        model.question = "1、指令改错 INC[SI]         ___________ MOV[BX],[SI]        ___________ MOV AX,[SI][DI]        ___________ MOV CS,1000       ___________";
        model.anwser = "INC PTR WORD[SI] ;MOV BX,[SI] ;MOV AX,[BX][DI] ;MOV AX,1000 ";


        model = new HuibianModel();
        model.question = "2、对于下面的符号定义，指出下列指令的错误。 A1  DB？ A2  DB  10 K1  EQU  1024 ①MOV  K1，AX______________________ ②MOV   A1，AX______________________ ③CMP   A1，A2______________________ ④K1   EQU   2048______________________";
        model.anwser = "①K1为常量，不能用MOV指令赋值 ;②A1为字节，AX为字变量，不匹配 ;③A1未定义，无法做比较指令 ;④K1重新赋值前，必须用PURGE释放 ";
        list.add(model);

        model = new HuibianModel();
        model.question = "3、调用程序与子程序之间的参数传递方法有三种，即寄存器、___________和___________。";
        model.anwser = "存储器（存储单元、地址表）； ;堆栈 ";
        list.add(model);

        model = new HuibianModel();
        model.question = "4、指令MOV  AH, BX有错，错误为(寄存器类型不匹配)，指令MOV  [BX], [SI]有错，错误为(不能都是存储器操作数)，指令MOV  AX, [SI][DI]有错，错误为([SI]和[DI]不能一起使用)。";
        model.anwser = "答案在题目中";
        list.add(model);

        model = new HuibianModel();
        model.question = "5、寻找操作数的过程就是操作数的___________。";
        model.anwser = "寻址方式 ";
        list.add(model);

        model = new HuibianModel();
        model.question = "6、指令MOV AX，（SI+COUNT）中，源操作数的寻址方式为___________，目的操作数的寻址方式为___________。";
        model.anwser = "存储器变址寻址 ;寄存器方式 ";
        list.add(model);

        model = new HuibianModel();
        model.question = "7、堆栈是一端___________，一端___________，按___________原理工作的一块存储区。";
        model.anwser = "固定 ;活动 ;先进后出 ";
        list.add(model);

        model = new HuibianModel();
        model.question = "8、将十六进制数AFH化为十进制是___________，化为二进制是___________化为八进制是___________。";
        model.anwser = "175 ;10101111 ;257 ";
        list.add(model);

        model = new HuibianModel();
        model.question = "9、当标志位___________=1时表示无符号数运算产生溢出，而当标志位___________=1 是表示带符号数运算产生溢出。";
        model.anwser = "CF ;OF ";
        list.add(model);

        model = new HuibianModel();
        model.question = "10、在CPU的标志寄存器中，其中SF是___________，ZF是___________，当SF＝1时，表示___________。";
        model.anwser = "符号标志 ;零标志 ;结果为负 ";
        list.add(model);


        model = new HuibianModel();
        model.question = "14、伪指令是___________,它不产生任何___________。";
        model.anwser = "它是在对源程序汇编期间由汇编程序处理的操作 ;机器代码 ";
        list.add(model);

        model = new HuibianModel();
        model.question = "17、某数据段的逻辑地址为FABC:0100,则数据段的段地址为___________,偏移地址为___________，物理地址为___________。";
        model.anwser = "FABCH ;0100H ;FACC0H ";
        list.add(model);

        model = new HuibianModel();
        model.question = "18、十六进制数0F1H表示的十进制正数表示为___________，表示的十进制负数表示为____________，十进制数255转换为BCD码表示为___________。";
        model.anwser = "241 ;-15 ;001001010101 ";
        list.add(model);

        model = new HuibianModel();
        model.question = "20、某数据段的逻辑地址为0FAA0:600,则数据段的段地址为___________,偏移地址为___________，物理地址为___________。";
        model.anwser = "FAA0h ;600h ;FB000h ";
        list.add(model);

        model = new HuibianModel();
        model.question = "21、CPU的标志寄存器中标志位，可以分为两大类，其中一类称为___________标志位，另一类称为___________标志位。";
        model.anwser = "状态 ;控制 ";
        list.add(model);

        model = new HuibianModel();
        model.question = "23、数-27的补码表示是___________（8位），扩展到16位为___________。";
        model.anwser = "11100101 ;1111111111100101 ";
        list.add(model);

        model = new HuibianModel();
        model.question = "24、假定(BX)=637DH,(SI)=2A9BH，位移量D=3237H，在立即寻址方式下的有效地址是___________，在直接寻址方式下的有效地址是___________，在使用BX的寄存器寻址方式下的有效地址是___________，在使用BX的间接寻址方式下的有效地址是___________，在使用BX的寄存器相对寻址方式下的有效地址是___________，在使用基址变址寻址方式下的有效地址是___________，在使用相对基址变址寻址方式下的有效地址是___________。";
        model.anwser = "0 ;3237H ;0 ;637DH ;95B4H ;8D19H ;BF50H ";
        list.add(model);

        model = new HuibianModel();
        model.question = "25、PC机中，可作为间接寻址的寄存器为___________。可作为8位和16位的寄存器为___________。";
        model.anwser = "BX，BP，SI，DI ;AX，BX，CX，DX ";
        list.add(model);

        model = new HuibianModel();
        model.question = "26、汇编语言源程序需经过___________程序汇编，___________程序连接才能生成可执行文件。";
        model.anwser = "Masm ;link ";
        list.add(model);

        model = new HuibianModel();
        model.question = "29、FLAGS称为___________________OF的值。其中OF=___________，SF=___________，ZF=___________，CF=___________。";
        model.anwser = "标志寄存器 ;OF=1 ;SF=1 ;ZF=0 ;CF=1 ";
        list.add(model);

        model = new HuibianModel();
        model.question = "30、IP是___________，它用来存放代码段中的___________。";
        model.anwser = "指令指示器 ;偏移地址（或EA） ";
        list.add(model);

        model = new HuibianModel();
        model.question = "31、将二进制数100010化为十进制是___________，将十进制数100化为八进制是___________。";
        model.anwser = "34 ;144 ";
        model = new HuibianModel();
        model.question = "33、指令MOV  BYTE PTR [BX], 1000有错，错误为_________________。";
        model.anwser = "1000超过了一个字节的范围 ";
        list.add(model);


        model = new HuibianModel();
        model.question = "36、要求屏蔽寄存器AL第0,1两位,则要执行指令___________;如果要求把AL的第0,1位变反，可使用指令___________。";
        model.anwser = "AND AL,0FCH ;XOR AL,03H ";
        list.add(model);

        model = new HuibianModel();
        model.question = "37、8086有4种逻辑段，他们分别是代码段、___________、___________、___________。";
        model.anwser = "数据段 ;堆栈段 ;附加数据段 ";
        list.add(model);


        model = new HuibianModel();
        model.question = "1、计算机中有一个“01100001”编码，如果把它认为是无符号数，它是十进制的___________，如果认为它是BCD码，则表示___________，又如果它是某个ASCII码，则代表字符___________。";
        model.anwser = "97 ;61 ;a ";
        list.add(model);

        model = new HuibianModel();
        model.question = "2、将二进制数111110化为十进制是___________，将十进制数31化为二进制为___________，化为八进制为___________。";
        model.anwser = "62 ;11111 ;37 ";
        list.add(model);

        model = new HuibianModel();
        model.question = "3、指令PUSH BL有错，应改为___________。";
        model.anwser = "PUSH BX ";
        list.add(model);




        model = new HuibianModel();
        model.question = "10、求出十六进制数1234与十六进制数62A0之和，并根据结果设置标志位SF、ZF、CF和OF的值，和为___________；SF=___________,ZF=___________,CF=___________,OF=___________。";
        model.anwser = "74D4H ;0 ;0 ;0 ;0 ";
        list.add(model);

        model = new HuibianModel();
        model.question = "15、PC机中，段首址应分别在段寄存器CS、DS、SS、ES中，其中，___________和___________的段首址由系统自动置入，___________和___________的段首址由用户自己置入。";
        model.anwser = "CS ;SS ;DS ;ES ";
        list.add(model);




        model = new HuibianModel();
        model.question = "将二进制数101010化为十进制是___________,十六进制___________,化为八进制___________";
        model.anwser = "42,2A,52 ";
        list.add(model);





        model = new HuibianModel();
        model.question = "对于256色，320×200的显示模式需要___________字节的内存存放一屏信息。";
        model.anwser = "64000";
        list.add(model);


        model = new HuibianModel();
        model.question = "2、某数据段的逻辑地址为F000:100,则数据段的段地址为F000H,偏移地址为100H，物理地址为F0100H。从逻辑地址为F000:100开始的一个8个字的数据区，其最后一个字的物理地址是F010EH。";
        model.anwser = "A. 正确";
        list.add(model);


        model = new HuibianModel();
        model.question = "4、在汇编语言中，寻找操作数的过程就是操作数的寻址，一般来说，存放在指令代码中的操作数称为立即数，存放在CPU的内部寄存器中的操作数称为寄存器操作数，存放在内部存储器中的操作数称为存储器操作数。";
        model.anwser = "A. 正确";
        list.add(model);

        model = new HuibianModel();
        model.question = "5、十六进制数0FFF8H表示的十进制正数为65528，表示的十进制负数为-8。";
        model.anwser = "A. 正确";
        list.add(model);





        model = new HuibianModel();
        model.question = "10、IP是指令指示器，它总是保存下一次将要从主存中取出指令的EA。";
        model.anwser = "A. 正确";
        list.add(model);

        model = new HuibianModel();
        model.question = "13、指令PUSH BL有错，应改为PUSH BX。";
        model.anwser = "A. 正确";
        list.add(model);

        model = new HuibianModel();
        model.question = "16、已知AL,BL中数据为带符号数，若求AL∕BL的商，应使用指令序列为CBW和IDIV BL。";
        model.anwser = "A. 正确";
        list.add(model);

        model = new HuibianModel();
        model.question = "18、PC机中，既可作为八位又可作为十六位寄存器使用的寄存器为AX、BX、CX、DX。\u200B";
        model.anwser = "A. 正确";
        list.add(model);

        model = new HuibianModel();
        model.question = "19、将十六进制数2EH化为十进制是62，将十六进制数10H化为二进制是11111，化为八进制是37。";
        model.anwser = "A. 正确";
        list.add(model);

        model = new HuibianModel();
        model.question = "20、指令MOV BX,AL有错，应改为MOV BX AX。";
        model.anwser = "A. 正确";
        list.add(model);



        model = new HuibianModel();
        model.question = "25、FLAGS称为标志寄存器，其中OF是溢出标志，SF是符号标志，ZF是零标志，CF是进位标志，当SF＝0时，表示结果为正数，当ZF＝1时，表示结果为0。";
        model.anwser = "A. 正确";
        list.add(model);

        model = new HuibianModel();
        model.question = "26、PC机中，段首址应分别在段寄存器CS、DS、SS、ES中，其中，CS和SS的段首址由系统自动置入，DS和ES的段首址由用户自己置入。";
        model.anwser = "A. 正确";
        list.add(model);


        model = new HuibianModel();
        model.question = "30、中断服务程序的入口地址称为中断向量。每个中断向量占个字节，其中，两个低字节存放中断服务程序的，两个高字节存放中断服务程序的4偏移地址段地址。";
        model.anwser = "A. 正确";
        list.add(model);



        model = new HuibianModel();
        model.question = "34、求出十六进制数1234与十六进制数62A0之和，并根据结果设置标志位SF、ZF、CF和OF的值，和为74D4H；SF=0,ZF=0,CF=0,OF=0。";
        model.anwser = "A. 正确";
        list.add(model);

        model = new HuibianModel();
        model.question = "35、计算机中有一个“01000001”编码，如果把它认为是无符号数，它是十进制的65，如果认为它是BCD码，则表示41，如果它是某个ASCII码，则代表字符A。";
        model.anwser = "A. 正确";
        list.add(model);



        model = new HuibianModel();
        model.question = "39、将二进制数110001化为十进制是49，十六进制为31，化为八进制为61。";
        model.anwser = "A. 正确";
        list.add(model);

        model = new HuibianModel();
        model.question = "2、FLAGS称为标志寄存器，十六进制数FFFFH与十六进制数62A0H相加，根据结果设置标志位SF、ZF、CF和OF的值。其中OF=1，SF=1，ZF=0，CF=1。 IP称为指令指示器，它用来存放代码段中的偏移地址（或EA）。";
        model.anwser = "A. 正确";
        list.add(model);

        model = new HuibianModel();
        model.question = "3、将十六进制数2EH化为十进制是62，将十六进制数10H化为二进制是11111，化为八进制是37。";
        model.anwser = "A. 正确";
        list.add(model);

        model = new HuibianModel();
        model.question = "5、将十进制数100化为十六进制是64，将十进制数100化为二进制是1100100。";
        model.anwser = "A. 正确";
        list.add(model);

        model = new HuibianModel();
        model.question = "8、求出十六进制数9D60与十六进制数62A0之和，并根据结果设置标志位SF、ZF、CF和OF的值，和为0000H；SF=0,ZF=1,CF=1,OF=0。";
        model.anwser = "A. 正确";
        list.add(model);

        model = new HuibianModel();
        model.question = "10、有下列数据定义： AA DW 1，2，3，4，5，6，7 COUNT EQU($-AA)/2 则COUNT=7，表示存储单元AA占有的字的个数。";
        model.anwser = "A. 正确";
        list.add(model);

        model = new HuibianModel();
        model.question = "11、若DS=0F3EH，SI=2000H，COUNT=0A8H，指令MOV AX，[SI+COUNT]中，源操作数的有效地址EA为20A8H，其物理地址为11488H。";
        model.anwser = "A. 正确";
        list.add(model);

        model = new HuibianModel();
        model.question = "12、假设程序中的数据定义如下： PARTNO  DW ? PNAME  DB   16 DUP (?) COUNT  DD? PLENTH   EQU  $-PARTNO 问PLENTH的值为16H。";
        model.anwser = "A. 正确";
        list.add(model);

        model = new HuibianModel();
        model.question = "13、某数据段的逻辑地址为A000:200,则数据段的段地址为A000h,偏移地址为200h，物理地址为A0200h。从逻辑地址为A000:200开始的一个8个字的数据区，其最后一个字的物理地址是A020eh。";
        model.anwser = "A. 正确";
        list.add(model);



        model = new HuibianModel();
        model.question = "17、假设VAR1和VAR2为字变量，LAB为标号，试指出下列指令的错误之处： ①ADD  VAR1,VAR2 不能都是存储器操作数\u200B ②SUB  AL, VAR1  数据类型不匹配 ③JMP LAB [SI]  LAB是标号而不是变量名，后面不能加[SI]④JNZ  VAR1    VAR1是变量而不是标号\u200B ⑤JMP NEAR LAB 应使用NEAR PTR\u200B";
        model.anwser = "A. 正确";
        list.add(model);

        model = new HuibianModel();
        model.question = "19、将二进制数111110化为十进制是62，将十进制数31化为二进制为11111，化为八进制为37。";
        model.anwser = "A. 正确";
        list.add(model);


        model = new HuibianModel();
        model.question = "21、中断服务程序的入口地址称为中断向量。每个中断向量占个字节，其中，两个低字节存放中断服务程序的，两个高字节存放中断服务程序的4偏移地址段地址。";
        model.anwser = "A. 正确";
        list.add(model);


        model = new HuibianModel();
        model.question = "27、将二进制数110001化为十进制是49，十六进制为31，化为八进制为61。";
        model.anwser = "A. 正确";
        list.add(model);


        model = new HuibianModel();
        model.question = "30、8086中有逻辑地址和物理地址，请将如下逻辑地址用物理地址表达： 逻辑地址：FFFFH：0        对应物理地址：0FFFF0H\u200B 逻辑地址：40H：17H        对应物理地址：417H 逻辑地址：2000H：4500H      对应物理地址：24500H 逻辑地址：B821H：4567H      对应物理地址：CC777H";
        model.anwser = "A. 正确";
        list.add(model);


        model = new HuibianModel();
        model.question = "32、将二进制数110000化为十进制是48，十六进制为30，化为八进制为60。";
        model.anwser = "A. 正确";
        list.add(model);


        model = new HuibianModel();
        model.question = "39、FLAGS称为标志寄存器，其中OF是溢出标志，SF是符号标志，ZF是零标志，CF是进位标志，当SF＝0时，表示结果为正数，当ZF＝1时，表示结果为0。";
        model.anwser = "A. 正确";
        list.add(model);

        model = new HuibianModel();
        model.question = "40、已知AL,BL中数据为带符号数，若求AL∕BL的商，应使用指令序列为CBW和IDIV BL。";
        model.anwser = "A. 正确";
        list.add(model);

        model = new HuibianModel();
        model.question = "在1000H单元中有一条二字节指令JMP SHORTLAB,如果其中的偏移量分别为30H.6CH.0B8H,则转向地址LAB的值分别为_________.__________._________";
        model.anwser = "1030H,106CH,10B8H";
        list.add(model);

        model = new HuibianModel();
        model.question = "有符号定义语句如下:BUFF DB.;1,2,3,'123' EBUFF DB.;0 L EQU EBUFF-BUFFL的值是__________";
        model.anwser = "6";
        list.add(model);

        model = new HuibianModel();
        model.question = "SP称为________,它用来存放当前栈顶的__________";
        model.anwser = "堆栈指示器,EA";
        list.add(model);

        model = new HuibianModel();
        model.question = "有下列数据定义:CC.;DW 0,2,4,6,8,10.12.14,16 COUNT EQU $-CC./2则COUNT=_______,表示______.";
        model.anwser = "9,表示存储单元CC占有的字的个数";
        list.add(model);

        model = new HuibianModel();
        model.question = "11、在指令MOV AX，[BX]中，源操作数的寻址方式为___________,目的操作数的寻址方式为___________。";
        model.anwser = "间接寻址 ;寄存器寻址 ";
        list.add(model);

        model = new HuibianModel();
        model.question = "将十六进制数FFH化为十进制是___________,化为二进制是__________,化为八进制是__________";
        model.anwser = "255,11111111,377";
        list.add(model);

        model = new HuibianModel();
        model.question = "指令MOV MYDAT.BX..SL.,ES:AX有错,错误为_______________,指令MOV  BYTE PTR [BX], 1000有错，错误为_________________。 ";
        model.anwser = "AX寄存器不能使用段超越,1000超过了一个字节的范围";
        list.add(model);

        model = new HuibianModel();
        model.question = "如果TABLE为数据段中0032单元的符号名,其中存放的内容为1234H,当执行指令 'MOVAX,TABLE'和'LEA.;AX,TABLE'后,AX.的内容分别为______._______";
        model.anwser = "1234H,0032";
        list.add(model);

        model = new HuibianModel();
        model.question = "若当前代码段大小为32K,CS.=0c018H,则该代码段第一个自己的物理地址为____________,最后一个字节的物理地址为_________";
        model.anwser = "0C0180H,C817FH";
        list.add(model);

        model = new HuibianModel();
        model.question = "将16进制数CDH化为十进制是______,化为二进制______,化为八进制_________";
        model.anwser = "205,11001101,315";
        list.add(model);

        model = new HuibianModel();
        model.question = "将2进制数100010化为十进制是______,将十进制数100化为八进制_____";
        model.anwser = "34,144";
        list.add(model);

        model = new HuibianModel();
        model.question = "[程序分析题]\n" +
                "MOV AH, 1\n" +
                "INT 21H;从键盘输入字符\n" +
                "AND; AL, 0FH\n" +
                "MOV BL， AL\n" +
                "MOV AH，1\n" +
                "INT 21H;\n" +
                "从键盘输入字符MOV AH, 0\n" +
                "AND:; AL，OFH\n" +
                "ADD; AL, BL\n" +
                "PUSH AXDAA\n" +
                "PUSH AX\n" +
                "\n" +
                "上述程序段执行时，若第一-次从键盘输入6, 第二次从键盘输入7.那么: .\n" +
                "1.第一次AX进栈的数是\n" +
                "2.第二次AX进栈的数是答案:\n" +
                "\n" +
                "(1)第一次AX进栈的数是( 000DH )\n" +
                "(2)第二次AX进栈的数是( 0103H)无\n" +
                "\n" +
                "\n" +
                "\n";
        model.anwser = "答案";
        list.add(model);

        model = new HuibianModel();
        model.question = "[程序分析题]\n" +
                "试分析下面的程序段完成什么功能?\n" +
                "MOV CL， 04\n" +
                "SHL DX,  CL\n" +
                "MOV  BL， AH\n" +
                "SHL  AX， CL\n" +
                "SHR  BL,  CL\n" +
                "OR  DL，BL答案:\n" +
                "\n" +
                "本程序段将((DX),(AX))的双字同时左移4位，即将此双字乘以10H (16)。无\n" ;
        model.anwser = "答案";
        list.add(model);

        model = new HuibianModel();
        model.question = "[程序分析题]\n" +
                "NUM DB.; ?\n" +
                "MOV AH, 1\n" +
                "INT 21H\n" +
                "CMP AL，39H\n" +
                "JBE NEXT\n" +
                "SUB:; AL，7\n" +
                "NEXT: SUB: AL, 30H\n" +
                "MOV NUM, AL\n" +
                "上述程序段运行后，若输入’F'，则NUM.=____用二进制表示.若’8’,则NUM.=__用二进制表示.该程序段的功能是\n" +
                "答案:\n" +
                "\n" +
                "1111 1000\n" +
                "功能:将1 6进制化成=进制。无\n";
        model.anwser = "答案";
        list.add(model);

        model = new HuibianModel();
        model.question = "[程序分析题]\n" +
                "有下列数据定义VA.; DB.; OFFH\n" +
                "VB.; DB.:;  1，2\n" +
                "VC; DW 3456H\n" +
                "执行下列指令序列:\n" +
                "MOV AX, WORD.: PTR VB.; 1\n" +
                "MOV BL, BYTE PTR VC; 1\n" +
                "MOV CL，VB1\n" +
                "则AX=___。 BL=____ ,CL=.\n" +
                "答案:\n" +
                "AX= 5602H BL= 34H CL= 02H\n";
        model.anwser = "答案";
        list.add(model);

        model = new HuibianModel();
        model.question = "[程序分析题]\n" +
                "MOV AL, 5\n" +
                "ADD.;AL，AL\n" +
                "MOV BL, AL\n" +
                "ADD:; AL，AL\n" +
                "ADD;AL，BL\n" +
                "上述程序段执行后，AL=___， BL=_\n" +
                "程序段的功能用数学表达式表示.\n" +
                "答案:\n" +
                "\n" +
                "AL=30, BL=10\n" +
                "将寄存器AL中的数乘以6并放回AL中。\n";
        model.anwser = "答案";
        list.add(model);

        model = new HuibianModel();
        model.question = "[程序分析题]\n" +
                "MOV BL, 64H\n" +
                "MOV CL, 03H\n" +
                "XOR AX, AX\n" +
                "AGAIN: ADD; AL, BL\n" +
                "ADC; AH, 0\n" +
                "DEC:; CL\n" +
                "JNZ AGAIN\n" +
                "问: 1.该程序段完成的功能是_____ :2.AX=_________\n" +
                "答案:\n" +
                "\n" +
                "(1)该程序段完成的功能是:将BL中的数乘3放入AX中\n" +
                "(2) AX=1 1000000\n" ;
        model.anwser = "答案";
        list.add(model);

        model = new HuibianModel();
        model.question = "";
        model.anwser = "答案";
        list.add(model);

        Collections.reverse(list);

        return list;
    }
}
