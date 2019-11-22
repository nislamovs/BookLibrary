package com.booklibrary.app.controllers.forTests;

//@Slf4j
//@RestController
//@RequiredArgsConstructor
//@RequestMapping(value = "/api/v1")
//@RequestMapping(value = "/test")
public class TestController {

//    private final TestService testService;
//    private final MongoService mongoService;
//
//    @GetMapping("/test")
//    public ResponseEntity<?> getAllUserList() {
//
//        log.info("Retrieving user list with all loans and extentions.");
//
//        return new ResponseEntity<String>("asdasddasd", HttpStatus.OK);
//    }
//
//    @Transactional
//    @GetMapping("/user")
//    public ResponseEntity<?> trackLogFeature() {
//
//        log.info("bla bla bla.");
//
//        return new ResponseEntity<String>(testService.findUser("dan"), HttpStatus.OK);
//    }
//
//    @GetMapping("/audit")
//    public ResponseEntity<?> saveData() {
//
//        log.info("Checking JPA Audit.");
//        RandomData rd = new RandomData(null, "vasja", "petrov", "asdas@xasd.bvv");
//
//        return new ResponseEntity<RandomData>(testService.saveUser(rd), HttpStatus.OK);
//    }

//    @GetMapping("/mongo")
//    public ResponseEntity<?> trackLogFeatureMongo() {
//
//        log.info("bla bla bla.");
//
//        return new ResponseEntity<List<Book>>(mongoService.findStatus("AA"), HttpStatus.OK);
//    }

//    @GetMapping("/ex")
//    public ResponseEntity<?> trackLogException() throws Exception {
//
//        log.info("bla bla bla.");
//
//        this.ss();
//
//        return new ResponseEntity<String>(testService.findUser("dan"), HttpStatus.OK);
//    }
//
//
//    private void ss() throws Exception {
//        throw new Exception("asdasdasdasdasd");
//    }


//db.history.aggregate([
//    {$lookup: {from: "debt",localField: "historyId",foreignField: "historyId",as: "debt"}},
//    {$unwind: {
//        path: "$debt",
//            preserveNullAndEmptyArrays: true
//    }},
//    {$lookup: {from: "payments",localField: "debt.debtId",foreignField: "debtId",as: "debt.payments"}},
//    {$match : {"historyId" : "3"}},
//])
//
//    @GetMapping("/mongorel")
//    public ResponseEntity<?> checkMongoRel() {
//
//        log.info("bla bla bla.");
//History hist = mongoService.getHistory("1");
//        System.out.println(">>>>.   "+hist);
//        return new ResponseEntity<History>(hist, HttpStatus.OK);
//    }
//
//    @GetMapping("/mongorelsave")
//    public ResponseEntity<?> makeMongoRel() {
//
//        Payment p = Payment.builder().amount(Money.of(19.99, "EUR")).bankAccount("45b908673948576390").debtId("1501").bookId("1055").bookNumber("1000")
//            .firstname("cadadas").lastname("dfsdfsdf").otherInfo("sdfsdfsdf").paymentId("2011").transactionNumber("asdasdasddasd").build();
//
//        Debt d = Debt.builder().actualPenalty(Money.of(19.99, "EUR")).bookNumber("1000").debtId("1501").historyId("1501").overdueDays(5).visitorId("3000").payments(ImmutableList.of(p)).build();
//
//        History h = History.builder().visitorId("3000").bookNumber("1000").comment("asdasdad").isFailedBookReturn(false).feedback(3).historyId("1501").debt(d)
//            .bookPickDate(Date.from(Instant.now())).bookExpectedReturnDate(Date.from(Instant.now())).build();
//
//
//
//
//        History hist = mongoService.saveHistory(h);
//        System.out.println(">>>>.   "+hist);
//        return new ResponseEntity<History>(hist, HttpStatus.OK);
//    }
//
//    @GetMapping("/mongorel2")
//    public ResponseEntity<?> checkMongoRel2() {
//
//        log.info("bla bla bla.");
//        List<Payment> hist = mongoService.getPayments("1");
//        System.out.println(">>>>.   "+hist);
//        return new ResponseEntity<List<Payment>>(hist, HttpStatus.OK);
//    }
//
//    @GetMapping("/mongorel3")
//    public ResponseEntity<?> checkMongoRel3() {
//
//        log.info("bla bla bla.");
//        Debt hist = mongoService.getDebt("1");
//        System.out.println(">>>>.   "+hist);
//        return new ResponseEntity<Debt>(hist, HttpStatus.OK);
//    }

//    @GetMapping("/mongorel4")
//    public ResponseEntity<?> checkMongoRel4() {
//
//        log.info("bla bla bla.");
//        Debt hist = mongoService.getHistory("1");
//        System.out.println(">>>>.   "+hist);
//        return new ResponseEntity<Debt>(hist, HttpStatus.OK);
//    }
}
