#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

int fibo(int n) {
	if (n == 0)
		return 0;
	else if (n == 1)
		return 1;
	else
		return fibo(n - 1) + fibo(n - 2);
}

int main() {

	int in, res;
	scanf("%d", &in);

	res = fibo(in);

	printf("%d", res);

	return 0;

}