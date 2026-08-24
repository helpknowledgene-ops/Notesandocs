ASSUME CS:CODE, DS:DATA

DATA SEGMENT

    STR DB 'EMBEDDING$'    ; String ending with $
    LEN DB ?               ; Variable to store length

DATA ENDS


CODE SEGMENT

START:

    ; Initialize Data Segment
    MOV AX, DATA
    MOV DS, AX

    ; Load starting address of string into DI
    LEA DI, STR

    ; Load '$' into AL
    ; '$' is the terminating character
    MOV AL, '$'

    ; Search maximum 65535 characters
    MOV CX, 0FFFFH

    ; Set Direction Flag = 0
    ; String scanning will move forward
    CLD

    ; Search for '$'
    ; DI is automatically incremented after every comparison
    ; CX is automatically decremented
    REPNE SCASB

    ; Calculate length
    ; AX = FFFFH - remaining CX
    MOV AX, 0FFFFH
    SUB AX, CX

    ; Remove the '$' character from the count
    DEC AX

    ; Store length
    MOV LEN, AL


    ; -------------------------
    ; Display the string
    ; -------------------------

    LEA DX, STR

    ; DOS function 09H
    ; Displays string ending with '$'
    MOV AH, 09H
    INT 21H


    ; -------------------------
    ; Exit program
    ; -------------------------

    MOV AH, 4CH
    INT 21H


CODE ENDS

END START
