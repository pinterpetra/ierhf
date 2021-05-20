/* Initial goals */

!get(food).   // initial goal: get a food
!get(juice).
//!check_bored. // initial goal: verify whether I am getting bored

+!get(food) : true
   <- .send(car, achieve, has(restaurant,food)).
+!get(juice) : true
   <- .send(motor, achieve, has(restaurant,juice)).

+has(restaurant,food) : true
   <- !drink(food).
-has(restaurant,food) : true
   <- !get(juice). //emiatt nem indul vissza a kocsi, emiatt v�rn� a restaurant a motort m�sodj�ra
+has(restaurant,juice) : true
   <- !drink(juice).
-has(restaurant,juice) : true
   <- !get(food).

// while I have food, sip
+!drink(food) : has(restaurant,food)
   <- sip(food);
     !drink(food).
+!drink(food) : not has(restaurant,food)
   <- true.
+!drink(juice) : has(restaurant,juice)
   <- sip(juice);
     !drink(juice).
+!drink(juice) : not has(restaurant,juice)
   <- true.

/*+!check_bored : true
   <- .random(X); .wait(X*5000+2000);  // i get bored at random times
      .send(car, askOne, time(_), R); // when bored, I ask the car about the time
      .print(R);
      !check_bored.*/
	  
/*+!check_needs : true
   <- .wait(4000);  // k�ne, hogy random v�lasszon a 3 besz�ll�tand� dolgob�l
      !get(food).;
	  <- .wait(4000);
	  !get(juice).*/

+msg(M)[source(Ag)] : true
   <- .print("Message from ",Ag,": ",M);
      -msg(M).

