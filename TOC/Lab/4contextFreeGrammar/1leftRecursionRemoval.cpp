/*Lab 4.1: WAP to remove the left recursion of given production
	E->E+T|T
	T->T*F|F
	F->(E)|id
*/
#include <stdio.h>
#include <string.h>

void removeLeftRecursion(char nonTerminal, char production[]) {
    char alpha[50], beta[50];
    int i, j = 0, k = 0;

    // If first production has left recursion
    if (production[0] == nonTerminal) {
        // Extract alpha (after left recursion)
        for (i = 1; production[i] != '|' && production[i] != '\0'; i++) {
            alpha[j++] = production[i];
        }
        alpha[j] = '\0';

        // Extract beta (after '|')
        if (production[i] == '|') i++;
        for (; production[i] != '\0'; i++) {
            beta[k++] = production[i];
        }
        beta[k] = '\0';

        // Print transformed productions
        printf("%c -> %s%c'\n", nonTerminal, beta, nonTerminal);
        printf("%c' -> %s%c' | e\n", nonTerminal, alpha, nonTerminal);
    } 
    else {
        // No left recursion
        printf("%c -> %s\n", nonTerminal, production);
    }
}

int main() {
    printf("Original Grammar:\n");
    printf("E -> E+T | T\n");
    printf("T -> T*F | F\n");
    printf("F -> (E) | id\n\n");

    printf("Grammar after Removing Left Recursion:\n");

    // For E
    removeLeftRecursion('E', "E+T|T");

    // For T
    removeLeftRecursion('T', "T*F|F");

    // For F (no left recursion)
    printf("F -> (E) | id\n");

    return 0;
}

