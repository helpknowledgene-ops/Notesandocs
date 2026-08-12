#include<stdio.h>

#define MAX 10
#define INF 9999

int main()
{
    int cost[MAX][MAX], dist[MAX], visited[MAX];
    int n, src, i, j, u, min;

    printf("Enter Number of Nodes: ");
    scanf("%d",&n);

    printf("Enter Cost Matrix:\n");
    for(i=0;i<n;i++)
        for(j=0;j<n;j++)
            scanf("%d",&cost[i][j]);

    printf("Enter Source Node: ");
    scanf("%d",&src);

    for(i=0;i<n;i++)
    {
        dist[i]=cost[src][i];
        visited[i]=0;
    }

    dist[src]=0;
    visited[src]=1;

    for(i=1;i<n;i++)
    {
        min=INF;

        for(j=0;j<n;j++)
        {
            if(!visited[j] && dist[j]<min)
            {
                min=dist[j];
                u=j;
            }
        }

        visited[u]=1;

        for(j=0;j<n;j++)
        {
            if(!visited[j] && dist[u]+cost[u][j]<dist[j])
                dist[j]=dist[u]+cost[u][j];
        }
    }

    printf("\nShortest Distances:\n");

    for(i=0;i<n;i++)
        printf("%d --> %d = %d\n",src,i,dist[i]);

    return 0;
}