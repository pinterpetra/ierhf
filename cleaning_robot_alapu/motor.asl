/* Initial beliefs and rules */

// initially, I believe that there is some juice in the mydepotmotor
available(juice,mydepotmotor).

// my restaurant should not consume more than 15 juices a day :-)
limit(juice,15).

too_much(B) :-
   .date(YY,MM,DD) &
   .count(consumed(YY,MM,DD,_,_,_,B),QtdB) &
   limit(B,Limit) &
   QtdB > Limit.


/* Plans */

+!has(restaurant,juice)
   :  available(juice,mydepotmotor) & not too_much(juice)
   <- !at(motor,mydepotmotor);
      open(mydepotmotor);
      get(juice);
      close(mydepotmotor);
      !at(motor,restaurant);
      hand_in(juice);
      ?has(restaurant,juice);
	  !at(motor,mydepotmotor);
      // remember that another juice has been consumed
      .date(YY,MM,DD); .time(HH,NN,SS);
      +consumed(YY,MM,DD,HH,NN,SS,juice).

+!has(restaurant,juice)
   :  not available(juice,mydepotmotor)
   <- .send(supermarket, achieve, order(juice,5));
      !at(motor,mydepotmotor). // go to mydepotmotor and wait there.

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
  <- move_towards_motor(P);
     !at(motor,P).

// when the supermarket makes a delivery, try the 'has' goal again
+delivered(juice,_Qtd,_OrderId)[source(supermarket)]
  :  true
  <- +available(juice,mydepotmotor);
     !has(restaurant,juice).

// when the mydepotmotor is opened, the juice stock is perceived
// and thus the available belief is updated
+stock(juice,0)
   :  available(juice,mydepotmotor)
   <- -available(juice,mydepotmotor).
+stock(juice,N)
   :  N > 0 & not available(juice,mydepotmotor)
   <- -+available(juice,mydepotmotor).

