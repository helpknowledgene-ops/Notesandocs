; Reg No: 24011017110

ASSUME CS:CODE, DS:DATA

DATA SEGMENT

    ; String ending with '$'
    ; '$' is used as the terminator for DOS function 09H
    STR DB 'satyam$'

    ; Variable to store the length of the string
    LEN DB ?

DATA ENDS


CODE SEGMENT

START:

    ; Initialize Data Segment
    MOV AX, DATA
    MOV DS, AX

    ; Initialize SI = 0
    ; SI will point to each character of the string
    MOV SI, 00H

    ; Initialize CL = 0
    ; CL will store the number of characters
    MOV CL, 00H


L1:

    ; Load the current character of the string into AL
    MOV AL, STR[SI]

    ; Load '$' into BL
    ; '$' indicates the end of the string
    MOV BL, '$'

    ; Compare current character with '$'
    CMP AL, BL

    ; If AL = '$', jump to L2
    ; This means the complete string has been counted
    JE L2

    ; Move SI to the next character
    INC SI

    ; Increase the character count
    INC CL

    ; Repeat the loop
    JMP L1


L2:

    ; Store the calculated length in LEN
    MOV LEN, CL

    ; Load address of STR into DX
    ; Required for DOS function 09H
    LEA DX, STR

    ; DOS function 09H
    ; Displays the string whose address is in DX
    MOV AH, 09H

    ; Call DOS interrupt
    INT 21H

    ; DOS function 4CH
    ; Terminates the program
    MOV AH, 4CH

    ; Call DOS interrupt to exit
    INT 21H


CODE ENDS

END START
