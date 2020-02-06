#include "parser.h"
#include "token.h"
#include "grammar.h"
#include "prints.h"



string parser::compile(string fn){
  startup(fn);
  if(scan.isError()){
    return scan.getError();
  }
  nextToken = scan.getNextToken();
  token t;
  t.setId(TOK_N_PROGRAM);
  tokStack.push(t);
  while(tokStack.empty() == false && scan.isError() == false && nextToken.getId() != TOK_NONE && nextToken.getId() != TOK_EOS){
    top = tokStack.top();
    tokStack.pop();
    if(isNonTerminal(top)){
      int prod_num = selectProd();
      if(prod_num != -1){
	cout << "TEST: PROD SELECTED=";
	printProduction(prod_num);
        pushProdRHS(prod_num);
      }
      //Couldn't get this error message to be here without breaking my program so I commented it out...
      //else{
      //  string error = "No production found for top=" + TOKENID_STR[top.getId()] + " nextToken=" + TOKENID_STR[nextToken.getId()];
      //  return error;
      //}
    }
    else{
      if(nextToken.getId() == top.getId()){
        cout << "TEST: " << TOKENID_STR[nextToken.getId()] << " consumed" << endl;
	nextToken = scan.getNextToken();
      }
      else{
        string error = TOKENID_STR[top.getId()] + " expected!";
        return error;
      }
    }
  }
  if(scan.isError()){
    return scan.getError();
  }
  if(tokStack.empty() == false && nextToken.getId() == TOK_EOS){//couldn't really test this one
    return "Unexpected end of source";
  }
  if(tokStack.empty() == true && nextToken.getId() != TOK_EOS){//couldn't really test this one
    return "Extra tokens at end of source";
  }
  if(nextToken.getId() != TOK_ERROR && (nextToken.getId() == TOK_NONE || nextToken.getId() == TOK_EOS)){
    return "";
  }
  return "REJECT";

}

int parser::selectProd(){
  for(int i = 0; i < GR_NUMPRODS; i++){
    if(top.getId() == PROD[i][0]){
      if(PROD[i][GR_FIRST_SELSET] == TOK_DEFAULT){
        return i;
      }
      else{
        for(int j = GR_FIRST_SELSET; j <= GR_LAST_SELSET; j++){
          if(nextToken.getId() == PROD[i][j]){
            return i;
          }
	}
      }
    }
  }
  return -1;
}


bool parser::isNonTerminal(token t){
  if(t.getId() >= FIRST_NON_TERMINAL && t.getId() <= LAST_NON_TERMINAL){
    return true;
  }
  return false;

}


void parser::pushProdRHS(int prodNum){
  for(int i = GR_LAST_RHS; i >= GR_FIRST_RHS; i--){
    token t;
    t.setId(PROD[prodNum][i]);
    tokStack.push(t);
  }
  return;

}

bool parser::startup(string filename){
  if(scan.open(filename) == 0){
    return false;
  }
  return true;

}
