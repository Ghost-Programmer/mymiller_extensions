/*******************************************************************************
 * Copyright 2018 MyMiller Consulting LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package name.mymiller.lang.validation;

import name.mymiller.lang.AdvancedString;

import java.util.regex.Pattern;

/**
 * Class to validate Credit Card and identify Credit Card Type.
 *
 * @author jmiller
 */
public class CreditCard {
    /**
     * VISA Pattern for validaiton
     */
    private static final String VISA_PATTERN = "^4[0-9]{12}(?:[0-9]{3})?$";
    /**
     * MasterCard Pattern for Validation
     */
    private static final String MASTERCARD_PATTERN = "^(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}$";
    /**
     * Diners Club Pattern for Validation
     */
    private static final String DINERS_CLUB_PATTERN = " ^3(?:0[0-5]|[68][0-9])[0-9]{11}$";
    /**
     * American Express Pattern for Validation
     */
    private static final String AMEX_PATTERN = " ^3[47][0-9]{13}$";
    /**
     * Discover Pattern for Validation
     */
    private static final String DISCOVER_PATTERN = "^6(?:011|5[0-9]{2})[0-9]{12}$";
    /**
     * JCB Pattern for Validation
     */
    private static final String JCB_PATTERN = "^(?:2131|1800|35\\d{3})\\d{11}$";

    /**
     * Valid Credit Card Pattern
     */
    private static final String VALID_CREDIT_CARD_PATTERN = "^(?:4[0-9]{12}(?:[0-9]{3})?|(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|6(?:011|5[0-9]{2})[0-9]{12}|(?:2131|1800|35\\d{3})\\d{11})$";

    /**
     * Regex Pattern for Visa
     */
    private final static Pattern visaPattern = Pattern.compile(CreditCard.VISA_PATTERN);
    /**
     * Regex Pattern for MasterCard
     */
    private final static Pattern masterCardPattern = Pattern.compile(CreditCard.MASTERCARD_PATTERN);
    /**
     * Regex Pattern for Diners Club
     */
    private final static Pattern dinersCludPattern = Pattern.compile(CreditCard.DINERS_CLUB_PATTERN);
    /**
     * Regex Pattern for American Express
     */
    private final static Pattern amexPattern = Pattern.compile(CreditCard.AMEX_PATTERN);
    /**
     * Regex Pattern for Discover
     */
    private final static Pattern discoverPattern = Pattern.compile(CreditCard.DISCOVER_PATTERN);
    /**
     * Regex Pattern for JCB
     */
    private final static Pattern jcbPattern = Pattern.compile(CreditCard.JCB_PATTERN);
    /**
     * Regex Pattern for valid credit card
     */
    private final static Pattern validCreditCardPattern = Pattern.compile(CreditCard.VALID_CREDIT_CARD_PATTERN);

    /**
     * Is the String a valid American Express Card
     *
     * @param cardNumber AdvancedString with the card number
     * @return true if it is valid.
     */
    public static boolean isAmericanExpress(final AdvancedString cardNumber) {
        return CreditCard.amexPattern.matcher(cardNumber.replaceAll("[^0-9]+", "").trim().toString()).matches();
    }

    /**
     * Is the String a valid Diners Club Card
     *
     * @param cardNumber AdvancedString with the card number
     * @return true if it is valid.
     */
    public static boolean isDinersClub(final AdvancedString cardNumber) {
        return CreditCard.dinersCludPattern.matcher(cardNumber.replaceAll("[^0-9]+", "").trim().toString()).matches();
    }

    /**
     * Is the String a valid Discover Card
     *
     * @param cardNumber AdvancedString with the card number
     * @return true if it is valid.
     */
    public static boolean isDiscover(final AdvancedString cardNumber) {
        return CreditCard.discoverPattern.matcher(cardNumber.replaceAll("[^0-9]+", "").trim().toString()).matches();
    }

    /**
     * Is the String a valid JCB Card
     *
     * @param cardNumber AdvancedString with the card number
     * @return true if it is valid.
     */
    public static boolean isJCB(final AdvancedString cardNumber) {
        return CreditCard.jcbPattern.matcher(cardNumber.replaceAll("[^0-9]+", "").trim().toString()).matches();
    }

    /**
     * Is the String a valid MasterCard Card
     *
     * @param cardNumber AdvancedString with the card number
     * @return true if it is valid.
     */
    public static boolean isMasterCard(final AdvancedString cardNumber) {
        return CreditCard.masterCardPattern.matcher(cardNumber.replaceAll("[^0-9]+", "").trim().toString()).matches();
    }

    /**
     * Is the String a valid credit card
     *
     * @param cardNumber AdvancedString with the card number
     * @return true if it is valid.
     */
    public static boolean isValidCreditCard(final AdvancedString cardNumber) {
        return CreditCard.validCreditCardPattern.matcher(cardNumber.replaceAll("[^0-9]+", "").trim().toString())
                .matches();
    }

    /**
     * Is the String a valid VISA Card
     *
     * @param cardNumber AdvancedString with the card number
     * @return true if it is valid.
     */
    public static boolean isVisa(final AdvancedString cardNumber) {
        return CreditCard.visaPattern.matcher(cardNumber.replaceAll("[^0-9]+", "").trim().toString()).matches();
    }
}
