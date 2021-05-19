/* Initial beliefs and rules */

// initially, I believe that there is some juice in the mydepot
available(juice,mydepot).

// my restaurant should not consume more than 15 juices a day :-)
limit(juice,15).

too_much(B) :-
   .date(YY,MM,DD) &
   .count(consumed(YY,MM,DD,_,_,_,B),QtdB) &
   limit(B,Limit) &
   QtdB > Limit.


/* Plans */

+!has(restaurant,juice)
   :  available(juice,mydepot) & not too_much(juice)
   <- !at(motor,mydepot);
      open(mydepot);
      get(juice);
      close(mydepot);
      !at(motor,restaurant);
      hand_in(juice);
      ?has(restaurant,juice);
      // remember that another juice has been consumed
      .date(YY,MM,DD); .time(HH,NN,SS);
      +consumed(YY,MM,DD,HH,NN,SS,juice).

+!has(restaurant,juice)
   :  not available(juice,mydepot)
   <- .send(supermarket, achieve, order(juice,5));
      !at(motor,mydepot). // go to mydepot and wait there.

+!has(restaurant,juice)
   :  too_much(juice) & limit(juice,L)
   <- .concat("The Department of Health does not allow me to give you more than ", L,
              " juices a day! I am very sorry about that!",M);
      .send(restaurant,tell,msg(M)).


-!has(_,_)
   :  true
   <- .current_intention(I);
      .print("Failed to achieve goal '!has(_,_)'. Current intention is: ",I).

+!at(motor,P) : at(motor,P) <- true.
+!at(motor,P) : not at(motor,P)
  <- move_towards(P);
     !at(motor,P).

// when the supermarket makes a delivery, try the 'has' goal again
+delivered(juice,_Qtd,_OrderId)[source(supermarket)]
  :  true
  <- +available(juice,mydepot);
     !has(restaurant,juice).

// when the mydepot is opened, the juice stock is perceived
// and thus the available belief is updated
+stock(juice,0)
   :  available(juice,mydepot)
   <- -available(juice,mydepot).
+stock(juice,N)
   :  N > 0 & not available(juice,mydepot)
   <- -+available(juice,mydepot).

+?time(T) : true
  <-  time.check(T).

