import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Simplex {
  Scanner scan = new Scanner(System.in);
  Scanner in = new Scanner(System.in);
  int coluna;
  int linha;
  float[][] Matrix;
  float[] vetSolu;
  float z;
  String tipo;

  // abrir o arquivo
  public void open()  {
      try {
          scan = new Scanner(new File("arquivo.txt"));
      } catch (FileNotFoundException ex) {
          Logger.getLogger(Simplex.class.getName()).log(Level.SEVERE, null, ex);
      }
  }

  // ler o arquivo
  public void readFile() {
    linha = scan.nextInt();
    coluna = scan.nextInt();
    Matrix = new float[linha][coluna];
    while (scan.hasNextInt()) {
      for (int i = 0; i < linha; i++) {
        for (int j = 0; j < coluna; j++) {
          Matrix[i][j] = scan.nextInt();
        }
      }
      tipo = scan.next().toString();
    }
    if(tipo.equals("MIN")){
      for (int i = 0; i < coluna; i++) {
          Matrix[Matrix.length-1][i] =  Matrix[Matrix.length-1][i]*-1;
      }
  }
  }

  // verificar se a loção é ótima
  private boolean verOtima() {
    for (int j = 0; j < coluna; j++) {
      if (Matrix[Matrix.length - 1][j] > 0) {
        return true;
      }
    }
    return false;
  }

  // selecionar o maior custo e retornar o index dela
  private int selecColunaIndexMaiouCusto() {
    float maiorCusto = Matrix[Matrix.length - 1][0];
    int index = 0;
    for (int j = 0; j < (coluna); j++) {
      if (maiorCusto < Matrix[Matrix.length - 1][j]) {
        maiorCusto = Matrix[Matrix.length - 1][j];
        index = j;
      }
    }
    return index;
  }

  // seleciona a linha do pivo
  private int seletcLinhaPivo() {
    int index = selecColunaIndexMaiouCusto();
    float[] vet = new float[linha - 1];
    for (int i = 0; i < linha - 1; i++) {
      vet[i] = (int) Matrix[i][coluna - 1] / Matrix[i][index];
    }

    float menor = 1000000000;
    int indice = 0;

    for (int j = 0; j < vet.length; j++) {
      if ((vet[j] > 0) && (menor > vet[j])) {
        menor = vet[j];
        indice = j;
      }
    }
    return indice;
  }

  private void escalonar() {
    int colunaP = selecColunaIndexMaiouCusto();
    int linhaP = seletcLinhaPivo();

    float pivo = Matrix[linhaP][colunaP];
    float numberMudar;

    for (int i = 0; i < coluna; i++) {
      if (pivo != 0) {
        Matrix[linhaP][i] = Matrix[linhaP][i] / pivo;
      }
    }
    pivo = Matrix[linhaP][colunaP];
    for (int i = 0; i < linha; i++) {
      numberMudar = Matrix[i][colunaP];
      if (i != linhaP) {
        for (int j = 0; j < coluna; j++) {
          Matrix[i][j] = (pivo * Matrix[i][j]) - (numberMudar * Matrix[linhaP][j]);
        }
      }
    }
  }

  private void mostrarSolucao() {
    float soma;
    int index = 0;
    vetSolu = new float[linha];
    for (int i = 0; i < linha; i++) {
      vetSolu[i] = Matrix[i][coluna - 1];
    }

    for (int j = 0; j < coluna - 1; j++) {
      soma = 0;
      for (int i = 0; i < linha; i++) {
        soma += Matrix[i][j];
      }

      if (soma == 1) {
        for (int i = 0; i < linha; i++) {
          if (Matrix[i][j] == 1) {
            index = i;
          }
        }
        System.out.println("Nao basica x" + (j + 1) + " = " + Matrix[index][coluna - 1]);
      } else {
        System.out.println("Basica x" + (j + 1) + " = 0");
      }
    }
    System.out.println("Z = " + -1 * Matrix[linha - 1][coluna - 1]);
    
  }

  public void solucionar() {
    while (verOtima()) {
      escalonar();
      printMat();
    }
    mostrarSolucao();
  }

  public void printMat() {
    System.out.println();
    for (int i = 0; i < linha; i++) {
      System.out.println();
      for (int j = 0; j < coluna; j++) {
        System.out.print(Matrix[i][j] + "\t");
      }
    }
    System.out.println();
  }

  public static void main(String[] args)  {
    Simplex Simplex = new Simplex();
    Simplex.open();
    Simplex.readFile();
    Simplex.solucionar();
  }
}