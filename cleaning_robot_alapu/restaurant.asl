/* Initial goals */

!get(food).   // initial goal: get a food
!get(juice).
!get(cookie).
//!check_bored. // initial goal: verify whether I am getting bored

+!get(food) : true
   <- .send(car, achieve, has(restaurant,food)).
+!get(juice) : true
   <- .send(motor, achieve, has(restaurant,juice)).
+!get(cookie) : true
   <- .send(bicycle, achieve, has(restaurant,cookie)).

+has(restaurant,food) : true
   <- !drink(food).
-has(restaurant,food) : true
   <- !get(food). //emiatt nem indul vissza a kocsi, emiatt várná a restaurant a motort másodjára
+has(restaurant,juice) : true
   <- !drink(juice).
-has(restaurant,juice) : true
   <- !get(juice).
+has(restaurant,cookie) : true
   <- !drink(cookie).
-has(restaurant,cookie) : true
   <- !get(cookie).

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
+!drink(cookie) : has(restaurant,cookie)
   <- sip(cookie);
     !drink(cookie).
+!drink(cookie) : not has(restaurant,cookie)
   <- true.

/*+!check_bored : true
   <- .random(X); .wait(X*5000+2000);  // i get bored at random times
      .send(car, askOne, time(_), R); // when bored, I ask the car about the time
      .print(R);
      !check_bored.*/
	  
/*+!check_needs : true
   <- .wait(4000);  // kéne, hogy random válasszon a 3 beszállítandó dolgoból
      !get(food).;
	  <- .wait(4000);
	  !get(juice).*/

+msg(M)[source(Ag)] : true
   <- //.print("Message from ",Ag,": ",M);
      -msg(M).

