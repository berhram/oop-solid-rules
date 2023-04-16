# Set of [ktlint](https://pinterest.github.io/ktlint/) rules to enforce OOP and SOLID principles
## Encapsulation
- Class members should be private or protected if class can be extended
## Inheritance
- All classes must be inherited
- If class if abstract, it cannot be inherited from other abstract classes (except for Fragment, Activity)
## Functions
- Classes' functions must be overriden from interfaces / abstract classes, if it is not unit test
- Interface cannot contain functions with default implementation
- Interface cannot contain more than 5 functions
- Function cannot have more than 5 parameters if it is not Retrofit service
## OOP only classes
- Open classes prohibited, use abstract classes instead
- Sealed classes prohibited
- Enums prohibited