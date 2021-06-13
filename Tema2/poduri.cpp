#include<iostream>
#include<fstream>
#include<vector>
#include<queue>
#include <cstring>

using namespace std;

/* structure that stores the coordinates of a point */
struct Coordinates {
	int x;
	int y;
};

/* function that verifies if at the next move, Gigel escapes the grid */
bool isGoal(char **grid, int x, int y, int n, int m) {
	/* the case where Gigel is on the first or the last line of the grid
	and the bridge he's on is a vertical one */
    if ((grid[x][y] == 'V') && (x == 0 || x == n - 1)) {
    	return true;
    }

    /* the case where Gigel is on the first or the last column of the grid
	and the bridge he's on is a horizontal one */
    if ((grid[x][y] == 'O') && (y == 0 || y == m - 1 )) {
		return true;
	}

	/* the case where Gigel is on one of the grid's boarders and the bridge 
	he's on is a double one */
	if ((grid[x][y] == 'D') &&
		(x == 0 || x == n - 1 || y == 0 || y == m - 1)) {
		return true;
	}

    return false;
}

/* function that does a BFS traversal of the grid finding the minimum distance
from the initial position to the grid's outer space */
int minDistBFS(char **grid, int n , int m, int x, int y) {
	/* arrays that help getting the adjacent positions when the current brigde
	is vertical, horizontal or double */
	int vertical_dx[] = { 1, -1 };
    int vertical_dy[] = { 0, 0 };

    int horizonatal_dx[] = { 0, 0 };
    int horizonatal_dy[] = { 1, -1 };

    int double_dx[] = { 1, -1, 0, 0 };
    int double_dy[] = { 0, 0, 1, -1 };

    /* queue that stores the positon that Gigel has visited */
    queue<pair<Coordinates, int> > q;

    /* verifies if the intial position is on one of the grid's boarders and 
    if Gigel can already escape */
    if (isGoal(grid, x - 1, y - 1, n, m)) {
        return 1;
    }

    Coordinates point = {x - 1, y - 1};
    q.push({point, 0});

    /* array that helps verifing if a brigde has been visited or not */
    int** visited = new int*[n];
    for (int i = 0; i < n; i++) {
		visited[i] = new int[m];
	}

	for (int i = 0; i < n; i++) {
		for (int j = 0; j < m; j++) {
			visited[i][j] = 0;
		}
	}

    while (!q.empty()) {
        pair<Coordinates, int> curr = q.front();
        Coordinates c = curr.first;

        /* verifies if Gigel can get out */
        if (isGoal(grid, c.x, c.y, n, m) == true) {
        	return curr.second + 1;
        }

        /* removes the first position from queue */
        q.pop();

        /* verifies if the current position represents a bridge or if it's a 
        portion of water; if it's a brigde goes to the adjacent positions based
        on the directions arrays */
        if (grid[c.x][c.y] == '.') {
            continue;
        } else if (grid[c.x][c.y] == 'D') {
        	/* gets the neighbors */
            for (int j = 0; j < 4; j++) {
                int x = c.x + double_dx[j];
                int y = c.y + double_dy[j];
                int dist = curr.second;

                if (grid[x][y] != '.' && !visited[x][y]) {
                	/* marks the bridge as visited and store into the queue the
                	new position */
                    visited[x][y] = 1;
                    Coordinates coord = {x, y};
                    q.push({coord, dist + 1});
                }
            }
        } else if (grid[curr.first.x][curr.first.y] == 'V') {
        	/* gets the neighbors */
            for (int j = 0; j < 2; j++) {
                int x = c.x + vertical_dx[j];
                int y = c.y + vertical_dy[j];
                int dist = curr.second;

                if (grid[x][y] != '.' && !visited[x][y]) {
                	/* marks the bridge as visited and store into the queue the
                	new position */
                    visited[x][y] = 1;
                    Coordinates coord = {x, y};
                    q.push({coord, dist + 1});
                }
            }
        } else {
            for (int j = 0; j < 2; j++) {
        		/* gets the neighbors */
				int x = c.x + horizonatal_dx[j];
                int y = c.y + horizonatal_dy[j];
                int dist = curr.second;

                if (grid[x][y] != '.' && !visited[x][y]) {
                	/* marks the bridge as visited and store into the queue the
                	new position */
                    visited[x][y] = 1;
                    Coordinates coord = {x, y};
                    q.push({coord, dist + 1});
                }
            }
        }
    }

    return -1;
}

int main() {
	int n, m, x, y;

	/* opens the input/output files */
	ifstream read;
	ofstream print;
	read.open("poduri.in");
	print.open("poduri.out");

	/* reads data from input file */
	read >> n >> m;
	read >> x >> y;

	char **grid = new char *[n];
	for (int i = 0; i < n; i++) {
		grid[i] = new char[m];
	}

	for (int i = 0; i < n; i++) {
		for (int j = 0; j < m; j++) {
	    	read >> grid[i][j];
		}
	}

	/* calculates the minimum distance */
	print << minDistBFS(grid, n, m, x, y) << endl;

	/* free the memory allocated for the grid*/
	for (int i = 0; i < n; i++) {
    	delete [] grid[i];
    }
	delete [] grid;

	/* close the files*/
	read.close();
	print.close();

	return 0;
}
