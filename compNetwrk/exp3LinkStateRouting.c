#include <stdio.h>

#define MAX 10
#define INF 9999

int main()
{
    int cost[MAX][MAX];
    int dist[MAX], visited[MAX];
    int n, src;
    int i, j, u, min;

    printf("Enter Number of Routers: ");
    scanf("%d", &n);

    printf("\nEnter Link Cost Matrix:\n");
    printf("(Enter %d for no direct link)\n\n", INF);

    for (i = 0; i < n; i++)
    {
        for (j = 0; j < n; j++)
        {
            scanf("%d", &cost[i][j]);
        }
    }

    printf("\nEnter Source Router: ");
    scanf("%d", &src);

    /* Initialize */
    for (i = 0; i < n; i++)
    {
        dist[i] = cost[src][i];
        visited[i] = 0;
    }

    dist[src] = 0;
    visited[src] = 1;

    /* Dijkstra's Algorithm */
    for (i = 1; i < n; i++)
    {
        min = INF;
        u = -1;

        /* Find minimum distance router */
        for (j = 0; j < n; j++)
        {
            if (!visited[j] && dist[j] < min)
            {
                min = dist[j];
                u = j;
            }
        }

        if (u == -1)
            break;

        visited[u] = 1;

        /* Update distances */
        for (j = 0; j < n; j++)
        {
            if (!visited[j] &&
                cost[u][j] != INF &&
                dist[u] + cost[u][j] < dist[j])
            {
                dist[j] = dist[u] + cost[u][j];
            }
        }
    }

    /* Display Link State Routing Information */
    printf("\n====================================\n");
    printf("       LINK STATE ROUTING\n");
    printf("====================================\n");

    printf("\nLink State Advertisement:\n");
    printf("Router %d broadcasts its link-state information.\n", src);

    printf("\nTopology Database Updated.\n");

    printf("\nShortest Paths from Router %d:\n", src);
    printf("------------------------------------\n");
    printf("Destination\tCost\n");
    printf("------------------------------------\n");

    for (i = 0; i < n; i++)
    {
        if (dist[i] == INF)
            printf("%d\t\tUnreachable\n", i);
        else
            printf("%d\t\t%d\n", i, dist[i]);
    }

    printf("------------------------------------\n");

    return 0;
}

// 5
// 0 2 5 9999 9999
// 2 0 3 4 9999
// 5 3 0 1 3
// 9999 4 1 0 2
// 9999 9999 3 2 0
// 0


// Enter Number of Routers: 5

// Enter Link Cost Matrix:
// (Enter 9999 for no direct link)

// Enter Source Router: 0

// ====================================
//        LINK STATE ROUTING
// ====================================

// Link State Advertisement:
// Router 0 broadcasts its link-state information.

// Topology Database Updated.

// Shortest Paths from Router 0:
// ------------------------------------
// Destination     Cost
// ------------------------------------
// 0               0
// 1               2
// 2               5
// 3               6
// 4               8
// ------------------------------------
