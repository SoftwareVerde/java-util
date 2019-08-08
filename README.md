# Java Utility

Miscellaneous java utility/convenience functions.

# Java Constable

Java immutability paradigm; provides compile-time checking via interface conventions.

## Motivation

Immutability in *Java* is usually handled by components via copying their internal objects when returning internal members.  Copying is often not a performance concern, since copying references is a fairly inexpensive operation for commonly-sized collections.  However, when returning collections of mutable objects, in order to truly maintain mutability, the objects within the collection must either be immutable or recursively be copied as well.  This requirement is repeated for each level of complexity (i.e. if mutable objects also expose/return mutable objects).

In *C++*, this problem is resolved via use of the `const` keyword.  While `const` is a reserved word in *Java*, it is not implemented and using it results in a compile-time error.  A vaguely similar concept to the *C++* const keyword in *Java* is `final`; however, `final` falls short of being a direct replacement to the immutability concept provided by *C++*'s `const`.  In *Java*, `final` creates what *C++* considers to be a const pointer, but not a const pointer to a const object&mdash;meaning that final variables may still mutate.

Example of mutating a final reference in *Java*:
```
final MyObject myObject = new MyObject();
myObject.setValue(0); // Mutates "myObject" despite being a final reference.

myObject = new MyObject(); // However, this is a compile error, because the first pointer to MyObject is final.
```

Mutability is ubiquitous in *Java*&mdash;in fact, *Java*'s standard library does not have an immutable interface for lists&mdash; the expected paradigm is to use `Collections.unmodifiableList()` which still returns a `java.util.List`, but whose mutating functions throw runtime exceptions&mdash; meaning, there is no compile-time checking and accidental mutations result in runtime errors.  *Java-Constable* provides such an interface, and provides an interface signature to create an immutable version of any mutable object.

# Approach

The *Java-Constable* paradigm involves having an immutable interface for any class that can mutate. The immutable interface should extend the `Constable` interface. The mutable version of this component should implement the immutable interface and provide mutable operations.

**Example**:

```
public interface Const { }                     // Provided by Java-Constable
public interface Constable<T extends Const> {  // Also provided by Java-Constable
    T asConst();
}

public interface Widget extends Constable<ImmutableWidget> {

    public int getSize();

    @Override
    ImmutableWidget asConst();
}

public class MutableWidget implements Widget {
    protected int _size;

    public void setSize(final int newSize) { _size = newSize; }

    @Override
    public int getSize() { return _size; }

    @Override
    public ImmutableWidget asConst() {
        return new ImmutableWidget(this);
    }
}

public class ImmutableWidget implements Widget, Const {

    protected int _size;

    public ImmutableWidget(final int size) { _size = size; }

    public ImmutableWidget(final Widget widget) { _size = widget.getSize(); }

    @Override
    public int getSize() { return _size; }

    @Override
    public ImmutableWidget asConst() {
        return this;
    }
}
```

The Constable interface has a single method, `asConst()`, which returns a `Const` object.  The implementation of `ImmutableWidget` implements the parent interface (which extends `Constable`) **and** it implements the `Const` interface.

The Mutable implementation of `asConst()` returns a copy of itself, as an `ImmutableWidget`.

It is useful to notice that the override of `Widget.asConst()` returns a more specific instance of `Const`&mdash;specifically, it returns an `ImmutableWidget`.

---
### Immutability Use-Cases

Immutability has 3 separate applications.
1. Exposing/returning an immutable object.
2. Changing internal state via a provided parameter.
3. Mutating a parameter.

#### 1. Exposing/returning an immutable object
The first case (exposing/returning an immutable object) is usually solved by copying the object before returning it to the user:
```
public class MyClass {
    protected final List<MyItem> _myItems = new ArrayList<MyItem>();

    public List<MyItem> getMyItems() {
        return new ArrayList<MyItem>(_myItems); // Copy the list of items...
    }
}
```
In this case, the receiving function that uses the list returned by `MyClass.getMyItems()` can mutate the list without mutating `MyClass`'s underlying list.  *(Note: if `MyItem` is mutable, then any changes to them will be propagated to `MyClass`.  In order to prevent this, the copy of `_myItems` would need to be a **deep copy**.)*

However, if the list we returned was immutable (a `com.softwareverde.constable.list.List` instead of a `java.util.List`), then exposing the list becomes trivial by simply returning the internal reference masked as an immutable list:
```
public class MyClass {
    protected final MutableList<MyItem> _myItems = new MutableList<MyItem>();

    public List<MyItem> getMyItems() {
        return _myItems; // Returning the direct reference to _myItems is safe, since its return type is an immutable interface of List (not java.util.List).
    }
}
```

#### 2. Changing internal state via a provided parameter

It may be important to prevent an external actor from mutating the instance's state by mutating a reference that was provided to it.  For instance, consider this design for an account that can serve multiple addresses.  Assume there are some addresses that cannot be served by certain accounts, depending on some series of business rules.

```
public interface Account extends Constable<ImmutableAccount> { }

public class MutableAccount implements Account {
    protected final MutableList<Address> _addresses = new MutableList<Address>;

    protected boolean _canServeAddress(Address address) { /* ... */ }

    public addAddress(Address address) throws UnserviceableAddressException {
        if (! _canServeAddress(address)) { throw new UnserviceableAddressException(); }

        _addresses.add(address.asConst());
    }
}
```
By invoking `address.asConst()` on the parameter before we store, we're ensuring that the address passed into `MutableAccount.addAddress()` cannot be changed by an outside actor.  Without `Address.asConst()`, it's possible that `address` could change after we've validated it.  Example:

```
MutableAddress address = new MutableAddress(Address.Region.OHIO);
MutableAccount account = new MutableAccount(Account.Region.AMERICA);
account.addAddress(address);

address.setRegion(Address.Region.ONTARIO); // Without asConst, our account may attempt to service a location within Ontario...
```

The utility of `address.asConst()` is that if `address` is already an immutable object, we don't copy it, we just share the reference--since `Address.asConst()` returns `this` for its immutable implementation.  It's only if `address` is mutable that we create a copy.

#### 3. Mutating a parameter

When creating a function signature, whether a parameter is mutable or immutable can very clearly distinguish the function's intent.

For instance, consider the following api design:
```
public interface RequestHeaders {
    public void addHeaders(WebRequest webRequest);
}
```

It's likely that this signature will take the internal list of headers from `RequestHeaders`, and apply them to the the `webRequest` parameter.   ...or maybe it extracts the headers from the webRequest to its internal list of headers.

Assuming `WebRequest` also follows the Java-Constable paradigm (and therefore, `WebRequest`, `ImmutableWebRequest`, and `MutableWebRequest` exist), the following alternative API designs become much more clear once the concept of immutability comes into play:

```
public interface RequestHeaders extends Constable<ImmutableRequestHeaders> { }

public class MutableRequestHeaders implements RequestHeaders {
    public void addHeaders(WebRequest webRequest); { /* ... */ }
}
```
In this example, the following aspects convey to the engineer that the intended functionality is to add headers to the `WebRequest` parameter:
1. `RequestHeaders.addHeaders()` is not defined within the immutable interface, which indicates invoking this function will likely modify the state of `MutableRequestHeaders`.
2. It is impossible for `MutableRequestHeaders.addHeaders()` to apply its headers to `webRequest` since `WebRequest` is immutable, therefore, this API will likely extract headers from the `WebRequest` and add them to the `RequestHeaders` object.

Alternatively, if the design was intended to add headers to a WebRequest, the API would likely be structured as:
```
public interface RequestHeaders extends Constable<ImmutableRequestHeaders> {
    public List<Header> getHeaders();
    public void addHeaders(MutableWebRequest webRequest);
}
```
In this example, the following aspects convey to the engineer that the intended functionality is to add headers to the `WebRequest` parameter:
1. The `RequestHeaders.addHeaders()` method is defined within the immutable interface.  Therefore, it should not be modifying `RequestHeaders`.
2. The parameter of `RequestHeaders.addHeaders()` takes a `MutableWebRequest`, not a `WebRequest`, indicating that this method signature intends on modifying the parameter's state.


## Pitfalls

1. It's possible to create circumstances that break the immutability contract.  The most obvious being creating a method within a `Const` implementation that mutates its internal state.  Mitigating this is significantly easier by ensuring the immutable implementation also uses immutable objects internally.  For instance:
```
public class ImmutableItemList implements ItemList, Const {
    protected final List<Item> _items;

    public ImmutableItemList(final List<Item> items) {
        _items = items.asConst();
    }
}
```

2. The less obvious pitfall may be encountered while using polymorphism.  For instance, consider the following implementation:
```
public class ImmutableClassA implements ClassA, Const {
    protected MutableValue _value = new MutableValue();

    public Value getValue() { return _value; } // While we want to be able to mutate _value ourselves, we don't want external actors to mutate it directly.  Returning the direct reference is okay to consider safe, since the type returned is an immutable interface, which should disallow anything from mutating it unless it is cast to a MutableValue.

    // ...
}

public class Main {
    protected static MutableValue _setupValue(MutableValue mutableValue) {
        mutableValue.setValue("someValue");
        return mutableValue;
    }

    protected static MutableValue _setupValue(Value value) {
        MutableValue mutableValue = new MutableValue(value);
        _setupValue(mutableValue);
        return mutableValue;
    }

    public static void main(String[] args) {
        ClassA classA = new ImmutableClassA();
        MutableValue mutableValue = _setupValue(classA.getValue()); // Whoops.  We've essentially cast classA to a MutableClassA without realizing it due to polymorphism...
    }
}
```

This (fairly contrived) example will accidentally mutate the internal member within `classA._value`, despite that it is an immutable interface and an `ImmutableClassA`.  Since `ImmutableClassA.getValue()` returns the an `MutableValue` masked as a `Value` immutable interface, when passed to `Main._setupValue()`, the most specific overloaded function is called, which in this case is `Main._setupValue(MutableValue)`. 

This pitfall is definitely not obvious, nor is it necessarily easy to understand, and therefore, it may be easy to recreate in production&mdash;and finding this bug could be very time consuming.  The easiest way to avoid this pitfall is to avoid overloading methods with a specific mutability and a nonspecific immutability.  For instance, had the methods been defined this way, this pitfall would not have been encountered:

```
    protected static MutableValue _setupValue(MutableValue mutableValue) { /* ... */ }
    protected static MutableValue _setupValue(ImmutableValue value) { /* ... */ }
```

Notice that the second overloaded signature is equally as specific as the first.  With this, the compiler would have raised an error, stating that `classA.getValue()` does not match either signature.  In response, the call to `Main._setupValue()` should be changed to:

```
MutableValue mutableValue = _setupValue(classA.getValue().asConst());
```

