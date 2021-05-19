/* Initial goals */

!get(food).   // initial goal: get a food
!check_bored. // initial goal: verify whether I am getting bored

+!get(food) : true
   <- .send(car, achieve, has(restaurant,food)).

+has(restaurant,food) : true
   <- !drink(food).
-has(restaurant,food) : true
   <- !get(food).

// while I have food, sip
+!drink(food) : has(restaurant,food)
   <- sip(food);
     !drink(food).
+!drink(food) : not has(restaurant,food)
   <- true.

+!check_bored : true
   <- .random(X); .wait(X*5000+2000);  // i get bored at random times
      .send(car, askOne, time(_), R); // when bored, I ask the car about the time
      .print(R);
      !check_bored.

+msg(M)[source(Ag)] : true
   <- .print("Message from ",Ag,": ",M);
      -msg(M).

