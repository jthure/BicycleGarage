BicycleGarage
=============

Classes
-------

###   Member
####  Attributes:
      * `String firstName, lastName`
      * `String pNbr, telNbr`
      * `String pincode`
      * `boolean suspended`
      * `Date checkedIn`

###   Bicycle
####  Attributes:
      * `String barcode`
      * `String ownerPin`
      * `boolean inGarage`

###   Database
####  Attributes:
      * `HashMap<String, Bicycle> bicycles`
      * `HashMap<String, Member> members`
      * `List<String> pincodes, barcodes`
      * `Collection Events`
