# DC-Tower
My implementation of the DC Tower Coding Challenge for IBM.

## Procedure 
The best way to run this project is to run the unit tests.



# Iterative improvements

## Recommended
* [x] Stefan Reformat code - always format code on agreed formatting (for us Intellij default) and remove unused imports
* [X] Stefan Review for thread safety and discuss with Martin
* [X] Martin Add some brief descriptive class level comments
* [X] Martin Add comment on algorithm on approach/idea on ElevatorService#getClosestElevator and #searchBestElevator
* [ ] Martin to talk Stefan through the code, discuss choices and trade offs made
* [X] Improve test cases and use assertThat or assertJ for assertions
    * [X] request elevator beyond lowest and highest floor
    * [X] request when all elevators are busy - #testElevatorServiceBigRequest
* [ ] analyse and discuss complexities in the algorithm (O(n^2), O(n), O(1))

## Optional
* [X] improvement: Introduce [Lombok](https://projectlombok.org/) library to reduce boilerplate code
* [ ] make ElevatorService#searchBestElevator delegate to an interface, AvailableElevatorFinder 
* [ ] minor: make highest and lowest floor configurable
* [ ] decouple concerns, this exercise we may not want to do, it probably just distracts us focusing too much on algorithmic improvements 
    * [ ] improvement: make number of elevators configurable
    * [ ] improvement: make highest and lowest floor configurable on Elevator instead on Service
    * [ ] improvement: then ElevatorService takes in a variable number of Elevators - mind the logic is probably getting more complex if elevators are limited to different floors
* [ ] minor: change Elevator#id to String, is more versatile as an id