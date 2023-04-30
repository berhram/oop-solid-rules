# Set of [ktlint](https://pinterest.github.io/ktlint/) rules to enforce OOP and SOLID principles

## Encapsulation

- Class members should be private or protected if the class can be extended

## Inheritance

- All classes must be inherited
- If a class is abstract, it cannot be inherited from other abstract classes (except
  for `Fragment`, `Activity`, `View`, `ViewGroup`, `ViewModel`)

## Functions

- Classes' functions must be overridden from interfaces / abstract classes, if it is not a unit test or `protected abstract`
- Interface cannot contain functions with default implementation
- Interface cannot contain more than 5 functions
- Function cannot have more than 5 parameters if it is not Retrofit service

## OOP-only classes

- Open classes prohibited, use abstract classes instead
- Sealed classes prohibited
- Enums prohibited

## Class layout

- Must satisfy [Kotlin Conventions class layout](https://kotlinlang.org/docs/coding-conventions.html#class-layout)