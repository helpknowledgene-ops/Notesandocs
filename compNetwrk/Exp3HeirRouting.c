#include<stdio.h>

int main()
{
    int cluster, node;

    printf("Enter Number of Clusters: ");
    scanf("%d",&cluster);

    printf("Enter Number of Nodes per Cluster: ");
    scanf("%d",&node);

    printf("\nHierarchical Network\n");

    for(int i=1;i<=cluster;i++)
    {
        printf("\nCluster %d\n",i);

        for(int j=1;j<=node;j++)
            printf("Node %d.%d\n",i,j);
    }

    printf("\nRouting occurs through Cluster Heads.\n");

    return 0;
}