##Lorraine Dto Test Gen

This was a library I wrote 5 years ago to allow me to generate test data. It allows differences between dtos to be declaratively specified through rules.
It allowed me to play with [fluent interfaces](http://martinfowler.com/bliki/FluentInterface.html). It's kind of lame but I'm saving it from googlecode closing more as a keepsake than anything else.  

###Description

This is a library that enables the conversion of a homogeneous collection of objects into a heterogeneous one based on the logic of a set of user defined rules.

The purpose of this could be to create a varied data set for use in unit tests or a realistic data set to allow full system testing.

These user defined rules are created programatically either through traditional object creation or via the usage of fluent interfaces.

These "rules" generally consist of an edit or change that should take place and the conditional that has to be matched to allow the edit to take place.

There are many built in conditionals and edits and provision has been made to allow complex conditionals to be built up and multiple edits to be encapsulated in to one rule.

###Creating a custom editor.

Rules are built using fluent interfaces:

```java
// Configure the TestUtilsContext to use a PropertySupportFactory
// This is used in introspection.
TestUtilsContext.useOgnl();

// Rules are created from the `Edits`factory and need an editor to start.
Edits.doThis(incrementEach("name").withBase("sample-"));

// Each subsequent chained method call return interfaces that only
// allows the writer to call methods in the correct order.
Edits.doThis(
		incrementEach("name").withBase("sample-").and(
				changeValueOf("date").to(new Date(System.currentTimeMillis()))));
```

This means that Rules can only be created when in a correct state

```java
Edit<SampleDto> edit1 = Edits.doThis(
								incrementEach("name").withBase("sample-") //
								 	.and(changeValueOf("date").to(new Date(System.currentTimeMillis())))) //
							 	.where(index().is(3).or(not(index().isOdd()))) //
								.forTheType(SampleDto.class);

Edit<SampleDto> edit2 = Edits.doThis(
								changeValueOf("name").to("CHANGED")) //
								.where(valueOf("name").is("sample-3")) //
								.forTheType(SampleDto.class);
```

Rules have helpful toString() methods that provide a readable representation of what they represent:

```java
System.out.println("RULE1:" + rule1);
System.out.println("RULE2:" + rule2);
```

Results in:
```
  RULE1:INCREMENT [name to 'sample-'+i] AND SET ['date' to 'Tue Nov 03 21:44:56 GMT+00:00 2009'] WHERE (INDEX [3]) OR (NOT [ODD]) 
  RULE2:SET ['name' to 'CHANGED'] WHERE EQUALS ['name' = 'sample-3']
```

Rules are added to a data editor:

```java
// Add the rules to a data editor
SimpleDataEditor<SampleDto> editor = SimpleDataEditor.create(); 
editor.add(edit1).add(edit2);
```

This can then be used in loops:

```java
List<SampleDto> dtos = range(0, 5)
						.mapToObj(i -> editor.edit(i, new SampleDto()))
						.collect(toList());
```

Assuming a class like:

```java
public class SampleDto {
  private String name;
  private Date date;

  @Override
  public String toString() {
    return "name:" + name + "\tdate:" + date;
  }
}
```

this...

```java
dtos.forEach(System.out::println);
```

...would print the following:

```
 name:sample-0          date:Tue Nov 03 21:44:56 GMT+00:00 2009
 name:null              date:null
 name:sample-2          date:Tue Nov 03 21:44:56 GMT+00:00 2009
 name:CHANGED           date:Tue Nov 03 21:44:56 GMT+00:00 2009
 name:sample-4          date:Tue Nov 03 21:44:56 GMT+00:00 2009
```
###Generating instances:

Use of a default value generator cache when creating dtos:

```java
//Register the utils context to use ognl for introspection
TestUtilsContext.useOgnl();

//Build the intance generator (which is responsible for creating new dtos of a specific type).
InstanceGenerator<TestDto3> generator = InstanceGenerator.create(TestDto3.class);

//Create a generation engine that is responsible for producing the dtos and applying visitors to them.
DtoGenerationEngine<TestDto3> engine = new DtoGenerationEngine<>(generator);

//Create and return 5 dtos
List<TestDto3> dtos = engine.collect(5);
assertEquals(5, dtos.size());

//When no ValueGeneratorCache is specified then a default cache will be used.
//This provides default values for primitive types, in the case of strings the value "DEFAULT"
assertEquals("DEFAULT", dtos.get(0).getName());
assertEquals("DEFAULT", dtos.get(0).getDescription());
```

Use of a custom value generator when generating dtos:

```java
//Register the utils context to use ognl for introspection
TestUtilsContext.useOgnl();
	
//Create a value generator cache
ValueGenerator cache = new DefaultValueGenerator(); //

//register the string generator against properties of type string called name.
//Note you can register generators against different criteria (See ValueGeneratorCache.java) 
cache.registerAPropertyNameAndTypeGenerator("name", String.class, () -> "DEFAULT_EXAMPLE_NAME");
		
//Build the generator with the custom cache.
InstanceGenerator<TestDto3> generator = InstanceGenerator.create(TestDto3.class, cache);
		
//Create a generation engine that is responsible for producing the dtos and applying visitors to them.
DtoGenerationEngine<TestDto3> engine = new DtoGenerationEngine<TestDto3>(generator);
		
//Create and return 1 dto
List<TestDto3> dtos = engine.collect(1);
assertEquals(1, dtos.size());
		
//Name has been set to the non-default value
assertEquals("DEFAULT_EXAMPLE_NAME", dtos.get(0).getName());
//Description is still set to the default value
assertEquals("DEFAULT", dtos.get(0).getDescription());
```
