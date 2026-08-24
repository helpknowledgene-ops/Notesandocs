ASSUME CS:CODE, DS:DATA

DATA SEGMENT

    STR DB 'MONISHA$'

DATA ENDS


CODE SEGMENT

START:

    ; Initialize Data Segment
    MOV AX, DATA
    MOV DS, AX

    ; SI points to first character
    MOV SI, 00H


L1:

    ; Load character into AL
    MOV AL, STR[SI]

    ; Check for end of string
    CMP AL, '$'
    JE L2

    ; Check whether character is below 'A'
    CMP AL, 'A'
    JB L3

    ; Check whether character is above 'Z'
    CMP AL, 'Z'
    JA L3

    ; Convert uppercase to lowercase
    ADD AL, 20H

    ; Store converted character
    MOV STR[SI], AL


L3:

    ; Move to next character
    INC SI

    ; Repeat
    JMP L1


L2:

    ; Display the string
    LEA DX, STR
    MOV AH, 09H
    INT 21H

    ; Exit program
    MOV AH, 4CH
    INT 21H


CODE ENDS

END START
