#include<stdio.h>

int main()
{
    int nodes, source, i;

    printf("Enter Number of Nodes: ");
    scanf("%d",&nodes);

    printf("Enter Source Node: ");
    scanf("%d",&source);

    printf("\nFlooding Process\n");

    for(i=0;i<nodes;i++)
    {
        if(i!=source)
            printf("Packet forwarded from %d to %d\n",source,i);
    }

    return 0;
}