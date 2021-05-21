
!get(food).
!get(juice).
!get(cookie).

+!get(food) : true
   <- .send(car, achieve, has(restaurant,food)).
+!get(juice) : true
   <- .send(motor, achieve, has(restaurant,juice)).
+!get(cookie) : true
   <- .send(bicycle, achieve, has(restaurant,cookie)).

+has(restaurant,food) : true
   <- !drink(food).
-has(restaurant,food) : true
   <- !get(food).
+has(restaurant,juice) : true
   <- !drink(juice).
-has(restaurant,juice) : true
   <- !get(juice).
+has(restaurant,cookie) : true
   <- !drink(cookie).
-has(restaurant,cookie) : true
   <- !get(cookie).

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


+msg(M)[source(Ag)] : true
   <- //.print("Message from ",Ag,": ",M);
      -msg(M).

