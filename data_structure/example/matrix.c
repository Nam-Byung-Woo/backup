Matrix* Add(Matrix* a, Matrix* b){
	if(a->max_cols != b->max_cols || 
		a->max_rows != b->max_rows){
		return NULL;
	}
	Matrix *result = Create(a->max_cols, a->max_rows);
	for(int y=0; y< result->max_rows; y++){
		for(int x=0; x < result->max_cols; x++){
			result->val[y][x] = a->val[y][x] + b->val[y][x];
		}
	}
	return result;
}

Matrix* Multifly(Matrix* a, Matrix* b){
	if(a->max_rows != b->max_cols){
		return NULL;
	}
	Matrix *result = Create(a->max_rows, a->max_cols);
	for(int y=0; y< result->max_rows; y++){
		for(int x=0; x < result->max_cols; x++){
			result->val[y][x] = 0;
			for(int i=0; i<a->max_cols; i++){
				result->val[y][x] += a->val[y][i] * b->val[i][x];
			}
		}
	}
	return result;
}

void Print(Matrix* m){
	int x, y;
	for(y=0; y< m->max_rows; y++){
		for(x=0; x < m->max_cols; x++){
			printf("%3.1f ", m->val[y][x]);
		}
		printf("\n");
	}
}

int main(){
	Matrix* a = Create(2, 1);
	Matrix* b = Create(1, 2);
	SetValue(a, 0, 0, 1);
	SetValue(b, 1, 0, 1);
	SetValue(b, 0, 0, 0);
	SetValue(b, 0, 1, 1);
	Matrix* r = Multiply(a, b);
	Print(r);

	free(a);
	free(b);
	free(r);
}