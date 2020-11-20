/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp01q2;

/*
Exercicio 01 -Um sapateiro faz 6 sapatos por hora, se fizer somente sapatos, e 5 cintos por hora, se fizer
somente cintos. Ele gasta 2 unidades de couro para fabricar 1 unidade de sapato e 1 unidade
couro para fabricar uma unidade de cinto. Sabendo-se que o total disponível de couro é de 6
unidades e que o lucro unitário por sapato é de 5 unidades monetárias e o do cinto é de 2
unidades monetárias, pede-se: o modelo do sistema de produção do sapateiro, se o objetivo é
maximizar seu lucro por hora.

maximizar F.O = 5x + 2y
sujeito a 2x+y<=6
          x,y >=0
 */




import gurobi .*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CITec
 */
public class Tp01q2 {

    
    public static void main(String[] args) {
        // TODO code application logic here
        //variaveis de decisao: quant de sapato e cinto para produzir por hora
        //    int x ->quant de sapato
        //    int y ->quant de cinto
        try {
          //cria o ambiente
          GRBEnv  env = new GRBEnv();
        
          //cria um objeto de modelo vazio
          GRBModel modelo = new GRBModel(env);
          
          //cria as variaveis de decisão - minimo
          GRBVar x = modelo.addVar(0.0,GRB.INFINITY,0.0, GRB.CONTINUOUS, "x");
          GRBVar y = modelo.addVar(0.0,GRB.INFINITY, 0.0, GRB.CONTINUOUS, "y");
          
          //construir a função objetiva vazia
          GRBLinExpr funcao = new GRBLinExpr();
          
          //construir a funcao objetiva
          funcao.addTerm(100.0, x);
          funcao.addTerm(150.0, y);
          
          //o que devemos fazer com a funçao objetiva: minimizar ou maximizar
          modelo.setObjective(funcao, GRB.MAXIMIZE);
          
          //criar a funcao das restriçoes do lado esquerdo
          funcao = new GRBLinExpr();
          funcao.addTerm(2.0, x);
          funcao.addTerm(3.0, y);
          
          //criar a desigualdade
          modelo.addConstr(funcao, GRB.LESS_EQUAL, 120.0, "restricao01");
          
          funcao = new GRBLinExpr();
          funcao.addTerm(1.0, x);
          
          //criar a desigualdade
          modelo.addConstr(funcao, GRB.LESS_EQUAL, 40.0, "restricao02");
           
          funcao = new GRBLinExpr();
          funcao.addTerm(1.0, y);
          
          //criar a desigualdade
          modelo.addConstr(funcao, GRB.LESS_EQUAL, 30.0, "restricao02");
          
          //otimizar o modelo
          modelo.optimize();
          
          //resultados obtidos
            System.out.println("**Solução Otima***");
          System.out.println("Quantidade de p1: " + x.get(GRB.StringAttr.VarName) + " = " + x.get(GRB.DoubleAttr.X) );
          System.out.println("Quantidade de P2 : " + y.get(GRB.StringAttr.VarName) + " = " + y.get(GRB.DoubleAttr.X) );
          
          //objetivo
            System.out.println("Maximizar o lucro da empresa");
            System.out.println("Objetivo: "+modelo.get(GRB.DoubleAttr.ObjVal));
          
            //
            modelo.dispose();
            funcao.clear();
        }
          
        catch (GRBException ex) {
            Logger.getLogger(Tp01q2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
