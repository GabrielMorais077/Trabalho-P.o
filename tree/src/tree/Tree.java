/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;

import gurobi .*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tree {

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
          funcao.addTerm(30000.0, x);
          funcao.addTerm(10000.0, y);
          
          
          //o que devemos fazer com a funçao objetiva: minimizar ou maximizar
          modelo.setObjective(funcao, GRB.MAXIMIZE);
          
          //criar a funcao das restriçoes do lado esquerdo
          funcao = new GRBLinExpr();
          funcao.addTerm(20.0, x);
          funcao.addTerm(10.0, y);
          
          //criar a desigualdade
          modelo.addConstr(funcao, GRB.LESS_EQUAL, 80.0, "restricao01");
          
          funcao = new GRBLinExpr();
          funcao.addTerm(1.0, x);
          funcao.addTerm(1.0, y);
          //criar a desigualdade
          modelo.addConstr(funcao, GRB.GREATER_EQUAL, 5.0, "restricao02");
           
         
          //otimizar o modelo
          modelo.optimize();
          
          //resultados obtidos
            System.out.println("**Solução Otima***");
          System.out.println("Quantidade de Frequencia semanal A: " + x.get(GRB.StringAttr.VarName) + " = " + x.get(GRB.DoubleAttr.X) );
          System.out.println("Quantidade de Frequencia semanal B : " + y.get(GRB.StringAttr.VarName) + " = " + y.get(GRB.DoubleAttr.X) );
          
          //objetivo
            System.out.println("Maximizar o numero de telespectadores: ");
            System.out.println("Objetivo: "+modelo.get(GRB.DoubleAttr.ObjVal));
          
            //
            modelo.dispose();
            funcao.clear();
        }
          
        catch (GRBException ex) {
            Logger.getLogger(Tree.class.getName()).log(Level.SEVERE, null, ex);
        }
    }







}

  