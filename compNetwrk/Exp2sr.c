include<stdio.h>

int main()
{
    int frames, window, ack[50], i;

    printf("Enter Total Number of Frames: ");
    scanf("%d",&frames);

    printf("Enter Window Size: ");
    scanf("%d",&window);

    for(i = 1; i <= frames; i++)
        ack[i] = 0;

    while(1)
    {
        int complete = 1;

        for(i = 1; i <= frames; i++)
        {
            if(!ack[i])
            {
                complete = 0;

                printf("\nFrame %d Sent\n", i);

                printf("Acknowledged? (1-Yes 0-No): ");
                scanf("%d",&ack[i]);

                if(!ack[i])
                    printf("Retransmitting Frame %d\n", i);
            }
        }

        if(complete)
            break;
    }

    printf("\nAll Frames Successfully Received.\n");

    return 0;
}
