/* Initial beliefs and rules */

// initially, I believe that there is some cookie in the mydepot
available(cookie,mydepot).

// my restaurant should not consume more than 5 cookies a day :-)
limit(cookie,5).

too_much(B) :-
   .date(YY,MM,DD) &
   .count(consumed(YY,MM,DD,_,_,_,B),QtdB) &
   limit(B,Limit) &
   QtdB > Limit.


/* Plans */

+!has(restaurant,cookie)
   :  available(cookie,mydepot) & not too_much(cookie)
   <- !at(bicycle,mydepot);
      open(mydepot);
      get(cookie);
      close(mydepot);
      !at(bicycle,restaurant);
      hand_in(cookie);
      ?has(restaurant,cookie);
      // remember that another cookie has been consumed
      .date(YY,MM,DD); .time(HH,NN,SS);
      +consumed(YY,MM,DD,HH,NN,SS,cookie).

+!has(restaurant,cookie)
   :  not available(cookie,mydepot)
   <- .send(supermarket, achieve, order(cookie,5));
      !at(bicycle,mydepot). // go to mydepot and wait there.

+!has(restaurant,cookie)
   :  too_much(cookie) & limit(cookie,L)
   <- .concat("The Department of Health does not allow me to give you more than ", L,
              " cookies a day! I am very sorry about that!",M);
      .send(restaurant,tell,msg(M)).


-!has(_,_)
   :  true
   <- .current_intention(I);
      .print("Failed to achieve goal '!has(_,_)'. Current intention is: ",I).

+!at(bicycle,P) : at(bicycle,P) <- true.
+!at(bicycle,P) : not at(bicycle,P)
  <- move_towards(P);
     !at(bicycle,P).

// when the supermarket makes a delivery, try the 'has' goal again
+delivered(cookie,_Qtd,_OrderId)[source(supermarket)]
  :  true
  <- +available(cookie,mydepot);
     !has(restaurant,cookie).

// when the mydepot is opened, the cookie stock is perceived
// and thus the available belief is updated
+stock(cookie,0)
   :  available(cookie,mydepot)
   <- -available(cookie,mydepot).
+stock(cookie,N)
   :  N > 0 & not available(cookie,mydepot)
   <- -+available(cookie,mydepot).

+?time(T) : true
  <-  time.check(T).

