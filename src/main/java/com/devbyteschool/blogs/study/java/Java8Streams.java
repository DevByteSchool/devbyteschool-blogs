package com.devbyteschool.blogs.study.java;

import com.sun.source.tree.Tree;
import org.springframework.util.comparator.Comparators;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Java8Streams {

    public static void main(String[] args) {

        System.out.println("Top 25 java 8 coding interview question and Ans.");

        List<Integer> nums = Arrays.asList(10,12,22,5,100,13,55,53,22,10,1,2);

        System.out.println("1) Given the list of integers, find the first element of the list using Stream functions ?");

        nums.stream().findFirst().ifPresent(System.out::println);

        System.out.println("2) Given a list of integers, find the total number of elements present in the list using Stream functions ?");

        System.out.println(nums.stream().count());

        System.out.println("3) Given a list of integers, find out all the even and odd numbers that exist in the list using Stream functions ?");

        nums.stream().filter(num -> num%2==0).forEach(System.out::println);

        nums.stream().filter(num -> num%2!=0).forEach(System.out::println);


        System.out.println("4) Given a list of integers, find out all the numbers starting with 5 using Stream functions ?");


        nums.stream().filter(num -> num.toString().startsWith("5")).forEach(System.out::println);

        System.out.println("5) How to find duplicate elements in a given integers list in java using Stream functions?");

        Set<Integer> temp = new HashSet<>();

        nums.stream().filter(num -> !temp.add(num)).forEach(System.out::println);


        System.out.println("6) Given a list of integers, find the maximum and minimum value element present in it using Stream functions?");

        Optional<Integer> max = nums.stream().max(Integer::compareTo);

        Optional<Integer> min =  nums.stream().min(Integer::compareTo);

        System.out.println(max.orElse(null));

        System.out.println(min.orElse(null));

        System.out.println("7). Given a list of integers, sort all the values present in it using Stream functions?");

        nums.stream().sorted().forEach(System.out::println);

        System.out.println("8). Given a list of integers, sort all the values present in it in descending order using Stream functions?");

        nums.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);

        System.out.println("9). Check array contain duplicate values or not.");

        int[] numbes = {1,2,4,5};

        if(Arrays.stream(numbes).distinct().count()!=numbes.length){
            System.out.println("It contain duplicate value");
        }else {
            System.out.println("It contain unique value");
        }

        System.out.println("10). Java 8 program to perform square on list elements and filter numbers greater than 50.");

        nums.stream().map(num -> num*num).filter(num -> num>50).forEach(System.out::println);


        System.out.println("11). Write a Java 8 program to sort an array and then convert the sorted array into Stream ?");

        Arrays.sort(numbes);

        Arrays.stream(numbes).forEach(System.out::println);

        // Medium Level

        System.out.println("12). How to use map to convert words into Uppercase in Java 8?");

        List<String> words = Arrays.asList("Dev","Byte","School","Dev","Byte");

        words.stream().map(word -> word.toUpperCase()).forEach(System.out::println);

        System.out.println("13). Write a Program to find the Maximum element in an array?");

        Arrays.stream(numbes).max().ifPresent(System.out::println);

        System.out.println("14). Write a Java 8 program to concatenate two Streams ?");

        Stream<Integer> s1 = Stream.of(1,2,3);

        Stream<Integer> s2 = Stream.of(4,5,6);

        Stream.concat(s1,s2).forEach(System.out::println);


        System.out.println("15). Write a program in stream to print 10 random.?");

        Random random = new Random();

        Stream.generate(random::nextInt).limit(10).forEach(System.out::println);

        System.out.println("16). Print date and time using java 8 feature ?");

        LocalDateTime localDateTime = LocalDateTime.now();

        System.out.println(localDateTime);

        System.out.println("17). Then format it in dd-MM-yyyy format ?");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        System.out.println(dateTimeFormatter.format(localDateTime));

        System.out.println("18). How to check if list is empty in Java 8 using Optional, " +
                "if not null iterate through the list and print the object? ?");
        List<String> list = null;

        Optional<List<String>> optionalList = Optional.ofNullable(list);

        optionalList.ifPresentOrElse(i-> i.stream().forEach(System.out::println),()->System.out.println("the  list is empty."));

        System.out.println("19). Given the array {10, 1, 20, 2, 100, 100, 100},write Java code " +
                "using Java Streams to determine the number of occurrences of a particular value.");

        int[] values = {10, 1, 20, 2, 100, 100, 100};

        int number = 100;

        long result = Arrays.stream(values).filter(value -> value == number).count();

        System.out.println(result);


        System.out.println("20). How to find only duplicate elements with its count from the String ArrayList in Java8 ?");

        Map<String,Long> map = words.stream().collect(Collectors.groupingBy(s->s,Collectors.counting()));

        map.entrySet().stream().filter(m -> m.getValue()>1).forEach(m -> System.out.println(m.getKey() +" : " +m.getValue()));


        System.out.println("21). Write a Program to give sum of salary of employee.?");

        Employee employee1=new Employee("test1",1000);
        Employee employee2=new Employee("test2",2000);
        Employee employee3=new Employee("test3",3000);
        Employee employee4=new Employee("test1",4000);

        List<Employee> employees=new ArrayList<>();

        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        employees.add(employee4);

        System.out.println(employees.stream().mapToDouble(Employee::getSalary).sum());

        System.out.println("22). Write a program to print the count of each character in a String using stream ?");

        String val= "DevByteSchool";

        Map<Character,Long> charCount = val.chars().mapToObj(c -> (char)c).collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));

        System.out.println(charCount);

        System.out.println("23). How to convert a List of objects into a Map by considering duplicated keys and store them in sorted order?");

        Map<String,Double> uniqueEmployee = employees.stream().collect(Collectors.toMap(Employee::getName,Employee::getSalary,
                (existing,replacement)->existing,()-> new TreeMap<>(Comparator.naturalOrder())));

        System.out.println(uniqueEmployee);

        System.out.println("24). Given a String, find the first non-repeated character in it using Stream functions?");


    }


}


