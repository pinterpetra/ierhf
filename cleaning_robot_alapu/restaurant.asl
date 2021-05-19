/* Initial goals */

!get(food).   // initial goal: get a food
!get(juice).
!get(cookie).
!check_bored. // initial goal: verify whether I am getting bored


+!get(food) : true
	//<- .send(car, achieve, has(restaurant,food)).
	
<-.random(Y);
	if(Y%3==0)
	{
		<- .send(car, achieve, has(restaurant,food)).
		<- .send(car, "Szia").
	}
	elif(Y%3 == 1)
	{
		<- .send(car, achieve, has(restaurant,food)).
		<- .send(car, "Szia bazd").
		//<- .send(motor, achieve, has(restaurant,food)).
	}
	elif(Y%3 == 2)
	{
		<- .send(car, achieve, has(restaurant,food)).
		<- .send(car, "Szia bazdmeg").
		//<- .send(bicycle, achieve, has(restaurant,food)).
	}
	
	
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

+!check_bored : true
   <- .random(X); .wait(X*5000+2000);  // i get bored at random times
      .send(car, askOne, time(_), R); // when bored, I ask the car about the time
      .print(R);
      !check_bored.

+msg(M)[source(Ag)] : true
   <- .print("Message from ",Ag,": ",M);
      -msg(M).

