/* Initial beliefs and rules */

// initially, I believe that there is some food in the mydepot
available(food,mydepot).

// my restaurant should not consume more than 10 foods a day :-)
limit(food,10).

too_much(B) :-
   .date(YY,MM,DD) &
   .count(consumed(YY,MM,DD,_,_,_,B),QtdB) &
   limit(B,Limit) &
   QtdB > Limit.


/* Plans */

+!has(restaurant,food)
   :  available(food,mydepot) & not too_much(food)
   <- !at(car,mydepot);
      open(mydepot);
      get(food);
      close(mydepot);
      !at(car,restaurant);
      hand_in(food);
      ?has(restaurant,food);
      // remember that another food has been consumed
      .date(YY,MM,DD); .time(HH,NN,SS);
      +consumed(YY,MM,DD,HH,NN,SS,food).

+!has(restaurant,food)
   :  not available(food,mydepot)
   <- .send(supermarket, achieve, order(food,5));
      !at(car,mydepot). // go to mydepot and wait there.

+!has(restaurant,food)
   :  too_much(food) & limit(food,L)
   <- .concat("The Department of Health does not allow me to give you more than ", L,
              " foods a day! I am very sorry about that!",M);
      .send(restaurant,tell,msg(M)).


-!has(_,_)
   :  true
   <- .current_intention(I);
      .print("Failed to achieve goal '!has(_,_)'. Current intention is: ",I).

+!at(car,P) : at(car,P) <- true.
+!at(car,P) : not at(car,P)
  <- move_towards(P);
     !at(car,P).

// when the supermarket makes a delivery, try the 'has' goal again
+delivered(food,_Qtd,_OrderId)[source(supermarket)]
  :  true
  <- +available(food,mydepot);
     !has(restaurant,food).

// when the mydepot is opened, the food stock is perceived
// and thus the available belief is updated
+stock(food,0)
   :  available(food,mydepot)
   <- -available(food,mydepot).
+stock(food,N)
   :  N > 0 & not available(food,mydepot)
   <- -+available(food,mydepot).

+?time(T) : true
  <-  time.check(T).

