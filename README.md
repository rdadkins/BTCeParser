# BTCeParser
BTCeParser is an API wrapper for <a href="https://btc-e.com">btc-e.com</a> built with Unirest

# Requirements
* Java 8

# What it does
* Parses information about all coins currently listed on the exchange.
* Gets depth, prices, and other general coin information.
* Full access to the TAPI (Trade API)
* Stores trading key + secret using AES 128 / 196 / 256.
* Allows for automatic updating requests on a given interval.
* Provides a base structure for automatic trading (bot).

# Parsing Public Info
All of the public methods used to parse information is stored in the <a href="https://github.com/rdadkins/BTCeParser/tree/master/core/src/main/java/co/bitsquared/btceparser/core">core</a> module. Public methods include coin information, ticker, depth, and recent trades. Each trading pair that btc-e offers is supplied in core/<a href="https://github.com/rdadkins/BTCeParser/blob/master/core/src/main/java/co/bitsquared/btceparser/core/TradingPair.java">TradingPair</a> and is used throughout core and trade modules. 

In order to create a request for public information, you need to create a <a href="https://github.com/rdadkins/BTCeParser/blob/master/core/src/main/java/co/bitsquared/btceparser/core/requests/PublicRequest.java">PublicRequest</a>. There are four pre-defined PublicRequest's found in core/<a href="https://github.com/rdadkins/BTCeParser/tree/master/core/src/main/java/co/bitsquared/btceparser/core/requests">requests</a> that handle parsing and correct URL addresses. Your implementing class must implement a subclass (sub interface) of <a href="https://github.com/rdadkins/BTCeParser/blob/master/core/src/main/java/co/bitsquared/btceparser/core/callbacks/BaseRequestCallback.java">BaseRequestCallback</a> if you are using one of the pre-defined requests.

An example of a pre-defined request is <a href="https://github.com/rdadkins/BTCeParser/blob/master/core/src/main/java/co/bitsquared/btceparser/core/requests/CoinInfoRequest.java">CoinInfoRequest</a> which needs to be supplied a <a href="https://github.com/rdadkins/BTCeParser/blob/master/core/src/main/java/co/bitsquared/btceparser/core/callbacks/CoinInfoCallback.java">CoinInfoCallback</a> and at least one TradingPair. Usage is shown as below:
```
CoinInfoRequest request = new CoinInfoRequest(this, TradingPair.BTC_USD);
request.processRequest();
...
@Override
public void onSuccess(CoinInfo coinInfo) {
    ...
}
```
When CoinInfoRequest is supplied with multiple TradingPair's, onSuccess() will be called however many times in relation to the amount of TradingPair's supplied.
Each pre-defined PublicRequest has their own callback with defined onSuccess() methods that return different objects. 

# Updating Public Requests
When there is a need to have a request update on a certain interval, each PublicRequest is defined to do so. Usage is below:
```
CoinInfoRequest request = new CoinInfoRequest(this, TradingPair.BTC_USD);
PublicUpdatingRequest updatingRequest = request.asUpdatingRequest();
updatingRequest.updateInterval(5, false);
updatingRequest.startUpdating();
...
@Override
public void onSuccess(CoinInfo coinInfo) {
    ...
}
```
Each PublicUpdatingRequest is defined to be set to a 10 second interval. This can be changed through updateInterval(interval, cancelCurrentTask). <b>Note: a PublicUpdatingRequest does not start until startUpdating() is called </b> and that is due to certain listeners being registered in certain places. To stop an UpdatingRequest, stopUpdating() needs to be called.

# Trade API (TAPI) and Authentication
BTCeParser also allows for TAPI access. In order for there to be a request to the TAPI, there needs to be signed information that is passed as headers on a key and secret (<a href="https://btc-e.com/tapi/docs">see btc-e docs for more info</a>). Key and secret storage as well as signing is handled in <a href="https://github.com/rdadkins/BTCeParser/blob/master/trade/src/main/java/co/bitsquared/btceparser/trade/authentication/Authenticator.java">Authenticator</a>.
There are two ways to create an Authenticator:

1. Supply it with raw text key and secret
    * This is should only be done once. You should save the this information with `Authenticator.writeToFile(file, password)`.
2. Load it from a file that is encrypted (StoredInfo.read(file, password))

The Authenticator also handles the nonce for you whenever you create a request or save it to a file.

Once you have an Authenticator, you are able to use an <a href="https://github.com/rdadkins/BTCeParser/blob/master/trade/src/main/java/co/bitsquared/btceparser/trade/requests/AccountRequest.java">AccountRequest</a> or any pre-defined subclasses. 

In Authenticator, the main method to look at is getHeaders(parameters). This method returns a map of headers which only contains 'key' and 'sign'. 'key' is just your API key and 'sign' is the signing of post data (parameters) with regards to the tapi documentation. 

# Account Requests (Private Requests via TAPI)
Just like the public requests, all private requests are already defined in the <a href="https://github.com/rdadkins/BTCeParser/tree/master/trade/src/main/java/co/bitsquared/btceparser/trade">trade</a> module which all extend <a href="https://github.com/rdadkins/BTCeParser/blob/master/trade/src/main/java/co/bitsquared/btceparser/trade/requests/AccountRequest.java">AccountRequest</a>. Each AccountRequest takes an Authenticator and its pre-defined callback. Some AccountRequests have required parameters in order to process request such as <a href="https://github.com/rdadkins/BTCeParser/blob/master/trade/src/main/java/co/bitsquared/btceparser/trade/requests/CancelOrderRequest.java">CancelOrderRequest</a> that requires an 'order_id' key to be defined.

Usage of <a href="https://github.com/rdadkins/BTCeParser/blob/master/trade/src/main/java/co/bitsquared/btceparser/trade/requests/ActiveOrderRequest.java">ActiveOrderRequest</a> which requires no parameters is shown below:
```
Authenticator auth = new Authenticator("key", "secret", 1);
ActiveOrderRequest request = new ActiveOrderRequest.Builder(auth).callback(this).build();
request.processRequest();
...
@Override
public void onSuccess(ActiveOrder[] activeOrders) {
    ...
}
```

When an AccountRequest requires parameters, you are able to see by calling getRequiredParams()

Here is <a href="https://github.com/rdadkins/BTCeParser/blob/master/trade/src/main/java/co/bitsquared/btceparser/trade/requests/TradeRequest.java">TradeRequest</a> that requires 'pair', 'type', 'rate', and 'amount' to be defined.
```
Authenticator auth = new Authenticator("key", "secret", 1);
TradeRequest request = new TradeRequest.Builder(auth).callback(this).build();
String[] requiredParams = request.getRequiredParams();
...
ParameterBuilder paramBuilder = ParameterBuilder.createBuilder().addTradingPair(TradingPair.BTC_USD).addOrderType(DepthType.BID).addRate(new Currency(500.0)).addAmount(Coin.fromSatoshi(5000000));
request.processRequest(paramBuilder); // method 1
request.assignParameters(paramBuilder); // or method 2
...
@Override
public void onSuccess(double received, double remains, int orderID, Funds[] funds) {
    ...
}
```
When you use assignParameters(), you assign an AccountRequest parameters (ParameterBuilder) but nothing is being processed until `processRequest()` is called.

# Updating Account Requests (Updating Private Requests)
There is also the ability to have AccountRequests update on an interval. Usage is similar to PublicUpdatingRequest. 

Here is an example of an updating TradeRequest:
```
Authenticator auth = new Authenticator("key", "secret", 1);
TradeRequest request = new TradeRequest.Builder(auth).callback(this).build();
request.assignParameters(paramBuilder);
UpdatingAccountRequest updatingRequest = request.asUpdatingRequest();
updatingRequest.startUpdating();
...
@Override
public void onSuccess(double received, double remains, int orderID, Funds[] funds) {
    ...
}
```
This still follows the same notion of having to call `startUpdating()` before anything actually happens.

# Currency / Coin

Some parts of the project use pre-determined <a href="https://github.com/rdadkins/BTCeParser/blob/master/core/src/main/java/co/bitsquared/btceparser/core/currency/BaseCurrency.java">BaseCurrency</a> implementations. The idea behind such complex currency systems is to keep certain logic at hand when doing math operations between two different types of BaseCurrency's. As it currently stands there is Currency, which handles all fiat based currencies, and Coin, which handle coin based currencies.

The following table shows how to use math operations and their outcome when performing that operation on two different BaseCurrency's.

| Math Operation   | Currency A      | Currency B      | Output of A (op) B |
|------------------|-----------------|-----------------|--------------------|
| Sum / Difference | Currency / Coin | Currency / Coin | Currency / Coin    |
| Product          | Currency        | Currency / Coin | Currency           |
| Product          | Coin            | Currency / Coin | Currency / Coin    |
| Quotient         | Currency        | Currency / Coin | Currency           |
| Quotient         | Coin            | Currency / Coin | Coin               |

Note: you can NOT take the sum / difference between Currency and Coin (and vice-versa) as the operation doesn't make sense. The first row simply states that you can take the sum / difference when they are the same type. Currency multiplied by anything will output a new Currency. Coin x Currency = Currency and Coin x Coin = Coin and so on.

# Future Goals

* GUI for public API and TAPI
* Logging public data to the users disk (at will)
* Bot implementation (in the works)

# Donation

If you think this project is useful feel free to donate to 1BVWmKHKRRms6UMWZaQZEwDXhHnLcbMpkx.