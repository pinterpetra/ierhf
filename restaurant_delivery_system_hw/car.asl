
available(food,mydepot).

limit(food,10).

too_much(B) :-
   .date(YY,MM,DD) &
   .count(consumed(YY,MM,DD,_,_,_,B),QtdB) &
   limit(B,Limit) &
   QtdB > Limit.


+!has(restaurant,food)
   :  available(food,mydepot) & not too_much(food)
   <- !at(car,mydepot);
      open(mydepot);
      get(food);
      close(mydepot);
      !at(car,restaurant);
      hand_in(food);
      ?has(restaurant,food);
	  !at(car,mydepot);
      .date(YY,MM,DD); .time(HH,NN,SS);
      +consumed(YY,MM,DD,HH,NN,SS,food).

+!has(restaurant,food)
   :  not available(food,mydepot)
   <- .send(supermarket, achieve, order(food,5));
      !at(car,mydepot).
	  
+!has(restaurant,food)
   :  too_much(food) & limit(food,L)
   <- .concat("I can't deliver more than ", L,
              " foods a day!",M);
      .send(restaurant,tell,msg(M)).


-!has(_,_)
   :  true
   <- .current_intention(I);
      .print("Failed to achieve goal '!has(_,_)'. Current intention is: ",I).

+!at(car,P) : at(car,P) <- true.
+!at(car,P) : not at(car,P)
  <- move_towards(P);
     !at(car,P).

+delivered(food,_Qtd,_OrderId)[source(supermarket)]
  :  true
  <- +available(food,mydepot);
     !has(restaurant,food).

+stock(food,0)
   :  available(food,mydepot)
   <- -available(food,mydepot).
+stock(food,N)
   :  N > 0 & not available(food,mydepot)
   <- -+available(food,mydepot).

