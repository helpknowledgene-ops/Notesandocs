ASSUME CS:CODE, DS:DATA

DATA SEGMENT
    A   DB 09H, 10H, 56H, 32H, 34H

    MIN DB ?
    MAX DB ?
    SUM DB ?
    AVG DB ?
DATA ENDS

CODE SEGMENT

START:
    MOV AX, DATA
    MOV DS, AX

    ; Initialize with first element
    MOV SI, 00H

    MOV AL, A[SI]       ; AL = maximum
    MOV BL, A[SI]       ; BL = minimum
    MOV DL, A[SI]       ; DL = sum

    MOV CL, 04H         ; 4 remaining elements

L1:
    INC SI              ; Move to next element

    ; Add element to sum
    ADD DL, A[SI]

    ; Check maximum
    CMP AL, A[SI]
    JAE L2              ; If AL >= A[SI], don't update maximum

    MOV AL, A[SI]       ; Update maximum

L2:
    ; Check minimum
    CMP BL, A[SI]
    JBE L3              ; If BL <= A[SI], don't update minimum

    MOV BL, A[SI]       ; Update minimum

L3:
    DEC CL
    JNZ L1

    ; Store minimum and maximum
    MOV MIN, BL
    MOV MAX, AL
    MOV SUM, DL

    ; Calculate average
    MOV AL, DL
    MOV AH, 00H
    MOV DL, 05H

    DIV DL              ; AL = quotient, AH = remainder

    MOV AVG, AL

    ; Exit
    MOV AH, 4CH
    INT 21H

CODE ENDS
END START
