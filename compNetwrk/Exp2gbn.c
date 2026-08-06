#include<stdio.h>

int main()
{
    int frames, window, i = 1, j;

    printf("Enter Total Number of Frames: ");
    scanf("%d",&frames);

    printf("Enter Window Size: ");
    scanf("%d",&window);

    while(i <= frames)
    {
        printf("\nSending Frames: ");

        for(j = i; j < i + window && j <= frames; j++)
        {
            printf("%d ", j);
        }

        printf("\nEnter last acknowledged frame: ");
        int ack;
        scanf("%d",&ack);

        if(ack < i)
        {
            printf("Timeout!! Retransmitting from Frame %d\n", i);
        }
        else
        {
            i = ack + 1;
        }
    }

    printf("\nAll Frames Successfully Transmitted.\n");

    return 0;
}
