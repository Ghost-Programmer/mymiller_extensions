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
package name.mymiller.lang.location;

import java.io.Serializable;
import java.util.Locale;
import java.util.Optional;

/**
 * Abstract class for creating Country Objects
 *
 * @author jmiller
 */
public enum Country implements CountryInterface, Serializable {
    // @formatter:off
    Afghanistan("Afghanistan", "AF", "AFG", "af", "AF", 4, "93", "Asia", new String[]{"fa-AF", "ps", "uz-AF", "tk"}),
    Albania("Albania", "AL", "ALB", "al", "AL", 8, "355", "Europe", new String[]{"sq", "el"}),
    Algeria("Algeria", "DZ", "DZA", "dz", "AG", 12, "213", "Africa", new String[]{"ar-DZ"}),
    AmericanSamoa("American Samoa", "AS", "ASM", "as", "AQ", 16, "1-684", "Oceania",
            new String[]{"en-AS", "sm", "to"}),
    Andorra("Andorra", "AD", "AND", "ad", "AN", 20, "376", "Europe", new String[]{"ca"}),
    Angola("Angola", "AO", "AGO", "ao", "AO", 24, "244", "Africa", new String[]{"pt-AO"}),
    Anguilla("Anguilla", "AI", "AIA", "ai", "AV", 660, "1-264", "North America", new String[]{"en-AI"}),
    Antarctica("Antarctica", "AQ", "ATA", "aq", "AY", 10, "672", "Antarctica", new String[]{null}),
    AntiguaAndBarbuda("Antigua and Barbuda", "AG", "ATG", "ag", "AC", 28, "1-268", "North America",
            new String[]{"en-AG"}),
    Argentina("Argentina", "AR", "ARG", "ar", "AR", 32, "54", "South America",
            new String[]{"es-AR", "en", "it", "de", "fr", "gn"}),
    Armenia("Armenia", "AM", "ARM", "am", "AM", 51, "374", "Asia", new String[]{"hy"}),
    Aruba("Aruba", "AW", "ABW", "aw", "AA", 533, "297", "North America", new String[]{"nl-AW", "es", "en"}),
    Australia("Australia", "AU", "AUS", "au", "AS", 36, "61", "Oceania", new String[]{"en-AU"}),
    Austria("Austria", "AT", "AUT", "at", "AU", 40, "43", "Europe", new String[]{"de-AT", "hr", "hu", "sl"}),
    Azerbaijan("Azerbaijan", "AZ", "AZE", "az", "AJ", 31, "994", "Asia", new String[]{"az", "ru", "hy"}),
    Bahamas("Bahamas", "BS", "BHS", "bs", "BF", 44, "1-242", "North America", new String[]{"en-BS"}),
    Bahrain("Bahrain", "BH", "BHR", "bh", "BA", 48, "973", "Asia", new String[]{"ar-BH", "en", "fa", "ur"}),
    Bangladesh("Bangladesh", "BD", "BGD", "bd", "BG", 50, "880", "Asia", new String[]{"bn-BD", "en"}),
    Barbados("Barbados", "BB", "BRB", "bb", "BB", 52, "1-246", "North America", new String[]{"en-BB"}),
    Belarus("Belarus", "BY", "BLR", "by", "BO", 112, "375", "Europe", new String[]{"be", "ru"}),
    Belgium("Belgium", "BE", "BEL", "be", "BE", 56, "32", "Europe", new String[]{"nl-BE", "fr-BE", "de-BE"}),
    Belize("Belize", "BZ", "BLZ", "bz", "BH", 84, "501", "North America", new String[]{"en-BZ", "es"}),
    Benin("Benin", "BJ", "BEN", "bj", "BN", 204, "229", "Africa", new String[]{"fr-BJ"}),
    Bermuda("Bermuda", "BM", "BMU", "bm", "BD", 60, "1-441", "North America", new String[]{"en-BM", "pt"}),
    Bhutan("Bhutan", "BT", "BTN", "bt", "BT", 64, "975", "Asia", new String[]{"dz"}),
    Bolivia("Bolivia", "BO", "BOL", "bo", "BL", 68, "591", "South America", new String[]{"es-BO", "qu", "ay"}),
    BosniaAndHerzegovina("Bosnia and Herzegovina", "BA", "BIH", "ba", "BK", 70, "387", "Europe",
            new String[]{"bs", "hr-BA", "sr-BA"}),
    Botswana("Botswana", "BW", "BWA", "bw", "BC", 72, "267", "Africa", new String[]{"en-BW", "tn-BW"}),
    Brazil("Brazil", "BR", "BRA", "br", "BR", 76, "55", "South America", new String[]{"pt-BR", "es", "en", "fr"}),
    BritishIndianOceanTerritory("British Indian Ocean Territory", "IO", "IOT", "io", "IO", 86, "246", "Asia",
            new String[]{"en-IO"}),
    BritishVirginIslAnds("British Virgin Islands", "VG", "VGB", "vg", "VI", 92, "1-284", "North America",
            new String[]{"en-VG"}),
    Brunei("Brunei", "BN", "BRN", "bn", "BX", 96, "673", "Asia", new String[]{"ms-BN", "en-BN"}),
    Bulgaria("Bulgaria", "BG", "BGR", "bg", "BU", 100, "359", "Europe", new String[]{"bg", "tr-BG"}),
    BurkinaFaso("Burkina Faso", "BF", "BFA", "bf", "UV", 854, "226", "Africa", new String[]{"fr-BF"}),
    Burundi("Burundi", "BI", "BDI", "bi", "BY", 108, "257", "Africa", new String[]{"fr-BI", "rn"}),
    Cambodia("Cambodia", "KH", "KHM", "kh", "CB", 116, "855", "Asia", new String[]{"km", "fr", "en"}),
    Cameroon("Cameroon", "CM", "CMR", "cm", "CM", 120, "237", "Africa", new String[]{"en-CM", "fr-CM"}),
    Canada("Canada", "CA", "CAN", "ca", "CA", 124, "1", "North America", new String[]{"en-CA", "fr-CA", "iu"}),
    CapeVerde("Cape Verde", "CV", "CPV", "cv", "CV", 132, "238", "Africa", new String[]{"pt-CV"}),
    CaymanIslAnds("Cayman Islands", "KY", "CYM", "ky", "CJ", 136, "1-345", "North America", new String[]{"en-KY"}),
    CentralAfricanRepublic("Central African Republic", "CF", "CAF", "cf", "CT", 140, "236", "Africa",
            new String[]{"fr-CF", "sg", "ln", "kg"}),
    Chad("Chad", "TD", "TCD", "td", "CD", 148, "235", "Africa", new String[]{"fr-TD", "ar-TD", "sre"}),
    Chile("Chile", "CL", "CHL", "cl", "CI", 152, "56", "South America", new String[]{"es-CL"}),
    China("China", "CN", "CHN", "cn", "CH", 156, "86", "Asia",
            new String[]{"zh-CN", "yue", "wuu", "dta", "ug", "za"}),
    ChristmasIslAnd("Christmas Island", "CX", "CXR", "cx", "KT", 162, "61", "Asia",
            new String[]{"en", "zh", "ms-CC"}),
    CocosIslAnds("Cocos Islands", "CC", "CCK", "cc", "CK", 166, "61", "Asia", new String[]{"ms-CC", "en"}),
    Colombia("Colombia", "CO", "COL", "co", "CO", 170, "57", "South America", new String[]{"es-CO"}),
    Comoros("Comoros", "KM", "COM", "km", "CN", 174, "269", "Africa", new String[]{"ar", "fr-KM"}),
    CookIslAnds("Cook Islands", "CK", "COK", "ck", "CW", 184, "682", "Oceania", new String[]{"en-CK", "mi"}),
    CostaRica("Costa Rica", "CR", "CRI", "cr", "CS", 188, "506", "North America", new String[]{"es-CR", "en"}),
    Croatia("Croatia", "HR", "HRV", "hr", "HR", 191, "385", "Europe", new String[]{"hr-HR", "sr"}),
    Cuba("Cuba", "CU", "CUB", "cu", "CU", 192, "53", "North America", new String[]{"es-CU"}),
    Curacao("Curacao", "CW", "CUW", "cw", "UC", 531, "599", "North America", new String[]{"nl", "pap"}),
    Cyprus("Cyprus", "CY", "CYP", "cy", "CY", 196, "357", "Europe", new String[]{"el-CY", "tr-CY", "en"}),
    CzechRepublic("Czech Republic", "CZ", "CZE", "cz", "EZ", 203, "420", "Europe", new String[]{"cs", "sk"}),
    DemocraticRepublicoftheCongo("Democratic Republic of the Congo", "CD", "COD", "cd", "CG", 180, "243", "Africa",
            new String[]{"fr-CD", "ln", "kg"}),
    Denmark("Denmark", "DK", "DNK", "dk", "DA", 208, "45", "Europe", new String[]{"da-DK", "en", "fo", "de-DK"}),
    Djibouti("Djibouti", "DJ", "DJI", "dj", "DJ", 262, "253", "Africa", new String[]{"fr-DJ", "ar", "so-DJ", "aa"}),
    Dominica("Dominica", "DM", "DMA", "dm", "DO", 212, "1-767", "North America", new String[]{"en-DM"}),
    DominicanRepublic("Dominican Republic", "DO", "DOM", "do", "DR", 214, "1-809, 1-829, 1-849", "North America",
            new String[]{"es-DO"}),
    EastTimor("East Timor", "TL", "TLS", "tl", "TT", 626, "670", "Oceania",
            new String[]{"tet", "pt-TL", "id", "en"}),
    Ecuador("Ecuador", "EC", "ECU", "ec", "EC", 218, "593", "South America", new String[]{"es-EC"}),
    Egypt("Egypt", "EG", "EGY", "eg", "EG", 818, "20", "Africa", new String[]{"ar-EG", "en", "fr"}),
    ElSalvador("El Salvador", "SV", "SLV", "sv", "ES", 222, "503", "North America", new String[]{"es-SV"}),
    EquatorialGuinea("Equatorial Guinea", "GQ", "GNQ", "gq", "EK", 226, "240", "Africa",
            new String[]{"es-GQ", "fr"}),
    Eritrea("Eritrea", "ER", "ERI", "er", "ER", 232, "291", "Africa",
            new String[]{"aa-ER", "ar", "tig", "kun", "ti-ER"}),
    Estonia("Estonia", "EE", "EST", "ee", "EN", 233, "372", "Europe", new String[]{"et", "ru"}),
    Ethiopia("Ethiopia", "ET", "ETH", "et", "ET", 231, "251", "Africa",
            new String[]{"am", "en-ET", "om-ET", "ti-ET", "so-ET", "sid"}),
    FalklAndIslAnds("Falkland Islands", "FK", "FLK", "fk", "FK", 238, "500", "South America", new String[]{"en-FK"}),
    FaroeIslAnds("Faroe Islands", "FO", "FRO", "fo", "FO", 234, "298", "Europe", new String[]{"fo", "da-FO"}),
    Fiji("Fiji", "FJ", "FJI", "fj", "FJ", 242, "679", "Oceania", new String[]{"en-FJ", "fj"}),
    FinlAnd("Finland", "FI", "FIN", "fi", "FI", 246, "358", "Europe", new String[]{"fi-FI", "sv-FI", "smn"}),
    France("France", "FR", "FRA", "fr", "FR", 250, "33", "Europe",
            new String[]{"fr-FR", "frp", "br", "co", "ca", "eu", "oc"}),
    FrenchPolynesia("French Polynesia", "PF", "PYF", "pf", "FP", 258, "689", "Oceania", new String[]{"fr-PF", "ty"}),
    Gabon("Gabon", "GA", "GAB", "ga", "GB", 266, "241", "Africa", new String[]{"fr-GA"}),
    Gambia("Gambia", "GM", "GMB", "gm", "GA", 270, "220", "Africa", new String[]{"en-GM", "mnk", "wof", "wo", "ff"}),
    Georgia("Georgia", "GE", "GEO", "ge", "GG", 268, "995", "Asia", new String[]{"ka", "ru", "hy", "az"}),
    Germany("Germany", "DE", "DEU", "de", "GM", 276, "49", "Europe", new String[]{"de"}),
    Ghana("Ghana", "GH", "GHA", "gh", "GH", 288, "233", "Africa", new String[]{"en-GH", "ak", "ee", "tw"}),
    Gibraltar("Gibraltar", "GI", "GIB", "gi", "GI", 292, "350", "Europe", new String[]{"en-GI", "es", "it", "pt"}),
    Greece("Greece", "GR", "GRC", "gr", "GR", 300, "30", "Europe", new String[]{"el-GR", "en", "fr"}),
    GreenlAnd("Greenland", "GL", "GRL", "gl", "GL", 304, "299", "North America", new String[]{"kl", "da-GL", "en"}),
    Grenada("Grenada", "GD", "GRD", "gd", "GJ", 308, "1-473", "North America", new String[]{"en-GD"}),
    Guam("Guam", "GU", "GUM", "gu", "GQ", 316, "1-671", "Oceania", new String[]{"en-GU", "ch-GU"}),
    Guatemala("Guatemala", "GT", "GTM", "gt", "GT", 320, "502", "North America", new String[]{"es-GT"}),
    Guernsey("Guernsey", "GG", "GGY", "gg", "GK", 831, "44-1481", "Europe", new String[]{"en", "fr"}),
    Guinea("Guinea", "GN", "GIN", "gn", "GV", 324, "224", "Africa", new String[]{"fr-GN"}),
    GuineaBissau("Guinea-Bissau", "GW", "GNB", "gw", "PU", 624, "245", "Africa", new String[]{"pt-GW", "pov"}),
    Guyana("Guyana", "GY", "GUY", "gy", "GY", 328, "592", "South America", new String[]{"en-GY"}),
    Haiti("Haiti", "HT", "HTI", "ht", "HA", 332, "509", "North America", new String[]{"ht", "fr-HT"}),
    Honduras("Honduras", "HN", "HND", "hn", "HO", 340, "504", "North America", new String[]{"es-HN"}),
    HongKong("Hong Kong", "HK", "HKG", "hk", "HK", 344, "852", "Asia", new String[]{"zh-HK", "yue", "zh", "en"}),
    Hungary("Hungary", "HU", "HUN", "hu", "HU", 348, "36", "Europe", new String[]{"hu-HU"}),
    IcelAnd("Iceland", "IS", "ISL", "is", "IC", 352, "354", "Europe",
            new String[]{"is", "en", "de", "da", "sv", "no"}),
    India("India", "IN", "IND", "in", "IN", 356, "91", "Asia",
            new String[]{"en-IN", "hi", "bn", "te", "mr", "ta", "ur", "gu", "kn", "ml", "or", "pa", "as", "bh", "sat",
                    "ks", "ne", "sd", "kok", "doi", "mni", "sit", "sa", "fr", "lus", "inc"}),
    Indonesia("Indonesia", "ID", "IDN", "id", "ID", 360, "62", "Asia", new String[]{"id", "en", "nl", "jv"}),
    Iran("Iran", "IR", "IRN", "ir", "IR", 364, "98", "Asia", new String[]{"fa-IR", "ku"}),
    Iraq("Iraq", "IQ", "IRQ", "iq", "IZ", 368, "964", "Asia", new String[]{"ar-IQ", "ku", "hy"}),
    IrelAnd("Ireland", "IE", "IRL", "ie", "EI", 372, "353", "Europe", new String[]{"en-IE", "ga-IE"}),
    IsleofMan("Isle of Man", "IM", "IMN", "im", "IM", 833, "44-1624", "Europe", new String[]{"en", "gv"}),
    Israel("Israel", "IL", "ISR", "il", "IS", 376, "972", "Asia", new String[]{"he", "ar-IL", "en-IL", null}),
    Italy("Italy", "IT", "ITA", "it", "IT", 380, "39", "Europe",
            new String[]{"it-IT", "de-IT", "fr-IT", "sc", "ca", "co", "sl"}),
    IvoryCoast("Ivory Coast", "CI", "CIV", "ci", "IV", 384, "225", "Africa", new String[]{"fr-CI"}),
    Jamaica("Jamaica", "JM", "JAM", "jm", "JM", 388, "1-876", "North America", new String[]{"en-JM"}),
    Japan("Japan", "JP", "JPN", "jp", "JA", 392, "81", "Asia", new String[]{"ja"}),
    Jersey("Jersey", "JE", "JEY", "je", "JE", 832, "44-1534", "Europe", new String[]{"en", "pt"}),
    Jordan("Jordan", "JO", "JOR", "jo", "JO", 400, "962", "Asia", new String[]{"ar-JO", "en"}),
    Kazakhstan("Kazakhstan", "KZ", "KAZ", "kz", "KZ", 398, "7", "Asia", new String[]{"kk", "ru"}),
    Kenya("Kenya", "KE", "KEN", "ke", "KE", 404, "254", "Africa", new String[]{"en-KE", "sw-KE"}),
    Kiribati("Kiribati", "KI", "KIR", "ki", "KR", 296, "686", "Oceania", new String[]{"en-KI", "gil"}),
    Kosovo("Kosovo", "XK", "XKX", null, "KV", 0, "383", "Europe", new String[]{"sq", "sr"}),
    Kuwait("Kuwait", "KW", "KWT", "kw", "KU", 414, "965", "Asia", new String[]{"ar-KW", "en"}),
    Kyrgyzstan("Kyrgyzstan", "KG", "KGZ", "kg", "KG", 417, "996", "Asia", new String[]{"ky", "uz", "ru"}),
    Laos("Laos", "LA", "LAO", "la", "LA", 418, "856", "Asia", new String[]{"lo", "fr", "en"}),
    Latvia("Latvia", "LV", "LVA", "lv", "LG", 428, "371", "Europe", new String[]{"lv", "ru", "lt"}),
    Lebanon("Lebanon", "LB", "LBN", "lb", "LE", 422, "961", "Asia", new String[]{"ar-LB", "fr-LB", "en", "hy"}),
    Lesotho("Lesotho", "LS", "LSO", "ls", "LT", 426, "266", "Africa", new String[]{"en-LS", "st", "zu", "xh"}),
    Liberia("Liberia", "LR", "LBR", "lr", "LI", 430, "231", "Africa", new String[]{"en-LR"}),
    Libya("Libya", "LY", "LBY", "ly", "LY", 434, "218", "Africa", new String[]{"ar-LY", "it", "en"}),
    Liechtenstein("Liechtenstein", "LI", "LIE", "li", "LS", 438, "423", "Europe", new String[]{"de-LI"}),
    Lithuania("Lithuania", "LT", "LTU", "lt", "LH", 440, "370", "Europe", new String[]{"lt", "ru", "pl"}),
    Luxembourg("Luxembourg", "LU", "LUX", "lu", "LU", 442, "352", "Europe", new String[]{"lb", "de-LU", "fr-LU"}),
    Macau("Macau", "MO", "MAC", "mo", "MC", 446, "853", "Asia", new String[]{"zh", "zh-MO", "pt"}),
    Macedonia("Macedonia", "MK", "MKD", "mk", "MK", 807, "389", "Europe",
            new String[]{"mk", "sq", "tr", "rmm", "sr"}),
    Madagascar("Madagascar", "MG", "MDG", "mg", "MA", 450, "261", "Africa", new String[]{"fr-MG", "mg"}),
    Malawi("Malawi", "MW", "MWI", "mw", "MI", 454, "265", "Africa", new String[]{"ny", "yao", "tum", "swk"}),
    Malaysia("Malaysia", "MY", "MYS", "my", "MY", 458, "60", "Asia",
            new String[]{"ms-MY", "en", "zh", "ta", "te", "ml", "pa", "th"}),
    Maldives("Maldives", "MV", "MDV", "mv", "MV", 462, "960", "Asia", new String[]{"dv", "en"}),
    Mali("Mali", "ML", "MLI", "ml", "ML", 466, "223", "Africa", new String[]{"fr-ML", "bm"}),
    Malta("Malta", "MT", "MLT", "mt", "MT", 470, "356", "Europe", new String[]{"mt", "en-MT"}),
    MarshallIslAnds("Marshall Islands", "MH", "MHL", "mh", "RM", 584, "692", "Oceania", new String[]{"mh", "en-MH"}),
    Mauritania("Mauritania", "MR", "MRT", "mr", "MR", 478, "222", "Africa",
            new String[]{"ar-MR", "fuc", "snk", "fr", "mey", "wo"}),
    Mauritius("Mauritius", "MU", "MUS", "mu", "MP", 480, "230", "Africa", new String[]{"en-MU", "bho", "fr"}),
    Mayotte("Mayotte", "YT", "MYT", "yt", "MF", 175, "262", "Africa", new String[]{"fr-YT"}),
    Mexico("Mexico", "MX", "MEX", "mx", "MX", 484, "52", "North America", new String[]{"es-MX"}),
    Micronesia("Micronesia", "FM", "FSM", "fm", "FM", 583, "691", "Oceania",
            new String[]{"en-FM", "chk", "pon", "yap", "kos", "uli", "woe", "nkr", "kpg"}),
    Moldova("Moldova", "MD", "MDA", "md", "MD", 498, "373", "Europe", new String[]{"ro", "ru", "gag", "tr"}),
    Monaco("Monaco", "MC", "MCO", "mc", "MN", 492, "377", "Europe", new String[]{"fr-MC", "en", "it"}),
    Mongolia("Mongolia", "MN", "MNG", "mn", "MG", 496, "976", "Asia", new String[]{"mn", "ru"}),
    Montenegro("Montenegro", "ME", "MNE", "me", "MJ", 499, "382", "Europe",
            new String[]{"sr", "hu", "bs", "sq", "hr", "rom"}),
    Montserrat("Montserrat", "MS", "MSR", "ms", "MH", 500, "1-664", "North America", new String[]{"en-MS"}),
    Morocco("Morocco", "MA", "MAR", "ma", "MO", 504, "212", "Africa", new String[]{"ar-MA", "fr"}),
    Mozambique("Mozambique", "MZ", "MOZ", "mz", "MZ", 508, "258", "Africa", new String[]{"pt-MZ", "vmw"}),
    Myanmar("Myanmar", "MM", "MMR", "mm", "BM", 104, "95", "Asia", new String[]{"my"}),
    Namibia("Namibia", "NA", "NAM", "na", "WA", 516, "264", "Africa",
            new String[]{"en-NA", "af", "de", "hz", "naq"}),
    Nauru("Nauru", "NR", "NRU", "nr", "NR", 520, "674", "Oceania", new String[]{"na", "en-NR"}),
    Nepal("Nepal", "NP", "NPL", "np", "NP", 524, "977", "Asia", new String[]{"ne", "en"}),
    NetherlAnds("Netherlands", "NL", "NLD", "nl", "NL", 528, "31", "Europe", new String[]{"nl-NL", "fy-NL"}),
    NetherlAndsAntilles("Netherlands Antilles", "AN", "ANT", "an", "NT", 530, "599", "North America",
            new String[]{"nl-AN", "en", "es"}),
    NewCaledonia("New Caledonia", "NC", "NCL", "nc", "NC", 540, "687", "Oceania", new String[]{"fr-NC"}),
    NewZealAnd("New Zealand", "NZ", "NZL", "nz", "NZ", 554, "64", "Oceania", new String[]{"en-NZ", "mi"}),
    Nicaragua("Nicaragua", "NI", "NIC", "ni", "NU", 558, "505", "North America", new String[]{"es-NI", "en"}),
    Niger("Niger", "NE", "NER", "ne", "NG", 562, "227", "Africa", new String[]{"fr-NE", "ha", "kr", "dje"}),
    Nigeria("Nigeria", "NG", "NGA", "ng", "NI", 566, "234", "Africa", new String[]{"en-NG", "ha", "yo", "ig", "ff"}),
    Niue("Niue", "NU", "NIU", "nu", "NE", 570, "683", "Oceania", new String[]{"niu", "en-NU"}),
    NorthKorea("North Korea", "KP", "PRK", "kp", "KN", 408, "850", "Asia", new String[]{"ko-KP"}),
    NorthernMarianaIslAnds("Northern Mariana Islands", "MP", "MNP", "mp", "CQ", 580, "1-670", "Oceania",
            new String[]{"fil", "tl", "zh", "ch-MP", "en-MP"}),
    Norway("Norway", "NO", "NOR", "no", "NO", 578, "47", "Europe", new String[]{"no", "nb", "nn", "se", "fi"}),
    Oman("Oman", "OM", "OMN", "om", "MU", 512, "968", "Asia", new String[]{"ar-OM", "en", "bal", "ur"}),
    Pakistan("Pakistan", "PK", "PAK", "pk", "PK", 586, "92", "Asia",
            new String[]{"ur-PK", "en-PK", "pa", "sd", "ps", "brh"}),
    Palau("Palau", "PW", "PLW", "pw", "PS", 585, "680", "Oceania",
            new String[]{"pau", "sov", "en-PW", "tox", "ja", "fil", "zh"}),
    Palestine("Palestine", "PS", "PSE", "ps", "WE", 275, "970", "Asia", new String[]{"ar-PS"}),
    Panama("Panama", "PA", "PAN", "pa", "PM", 591, "507", "North America", new String[]{"es-PA", "en"}),
    PapuaNewGuinea("Papua New Guinea", "PG", "PNG", "pg", "PP", 598, "675", "Oceania",
            new String[]{"en-PG", "ho", "meu", "tpi"}),
    Paraguay("Paraguay", "PY", "PRY", "py", "PA", 600, "595", "South America", new String[]{"es-PY", "gn"}),
    Peru("Peru", "PE", "PER", "pe", "PE", 604, "51", "South America", new String[]{"es-PE", "qu", "ay"}),
    Philippines("Philippines", "PH", "PHL", "ph", "RP", 608, "63", "Asia", new String[]{"tl", "en-PH", "fil"}),
    Pitcairn("Pitcairn", "PN", "PCN", "pn", "PC", 612, "64", "Oceania", new String[]{"en-PN"}),
    PolAnd("Poland", "PL", "POL", "pl", "PL", 616, "48", "Europe", new String[]{"pl"}),
    Portugal("Portugal", "PT", "PRT", "pt", "PO", 620, "351", "Europe", new String[]{"pt-PT", "mwl"}),
    PuertoRico("Puerto Rico", "PR", "PRI", "pr", "RQ", 630, "1-787, 1-939", "North America",
            new String[]{"en-PR", "es-PR"}),
    Qatar("Qatar", "QA", "QAT", "qa", "QA", 634, "974", "Asia", new String[]{"ar-QA", "es"}),
    RepublicoftheCongo("Republic of the Congo", "CG", "COG", "cg", "CF", 178, "242", "Africa",
            new String[]{"fr-CG", "kg", "ln-CG"}),
    Reunion("Reunion", "RE", "REU", "re", "RE", 638, "262", "Africa", new String[]{"fr-RE"}),
    Romania("Romania", "RO", "ROU", "ro", "RO", 642, "40", "Europe", new String[]{"ro", "hu", "rom"}),
    Russia("Russia", "RU", "RUS", "ru", "RS", 643, "7", "Europe",
            new String[]{"ru", "tt", "xal", "cau", "ady", "kv", "ce", "tyv", "cv", "udm", "tut", "mns", "bua", "myv",
                    "mdf", "chm", "ba", "inh", "tut", "kbd", "krc", "ava", "sah", "nog"}),
    RwAnda("Rwanda", "RW", "RWA", "rw", "RW", 646, "250", "Africa", new String[]{"rw", "en-RW", "fr-RW", "sw"}),
    SaintBarthelemy("Saint Barthelemy", "BL", "BLM", "gp", "TB", 652, "590", "North America", new String[]{"fr"}),
    SaintHelena("Saint Helena", "SH", "SHN", "sh", "SH", 654, "290", "Africa", new String[]{"en-SH"}),
    SaintKittsAndNevis("Saint Kitts and Nevis", "KN", "KNA", "kn", "SC", 659, "1-869", "North America",
            new String[]{"en-KN"}),
    SaintLucia("Saint Lucia", "LC", "LCA", "lc", "ST", 662, "1-758", "North America", new String[]{"en-LC"}),
    SaintMartin("Saint Martin", "MF", "MAF", "gp", "RN", 663, "590", "North America", new String[]{"fr"}),
    SaintPierreAndMiquelon("Saint Pierre and Miquelon", "PM", "SPM", "pm", "SB", 666, "508", "North America",
            new String[]{"fr-PM"}),
    SaintVincentAndtheGrenadines("Saint Vincent and the Grenadines", "VC", "VCT", "vc", "VC", 670, "1-784",
            "North America", new String[]{"en-VC", "fr"}),
    Samoa("Samoa", "WS", "WSM", "ws", "WS", 882, "685", "Oceania", new String[]{"sm", "en-WS"}),
    SanMarino("San Marino", "SM", "SMR", "sm", "SM", 674, "378", "Europe", new String[]{"it-SM"}),
    SaoTomeAndPrincipe("Sao Tome and Principe", "ST", "STP", "st", "TP", 678, "239", "Africa",
            new String[]{"pt-ST"}),
    SaudiArabia("Saudi Arabia", "SA", "SAU", "sa", "SA", 682, "966", "Asia", new String[]{"ar-SA"}),
    Senegal("Senegal", "SN", "SEN", "sn", "SG", 686, "221", "Africa", new String[]{"fr-SN", "wo", "fuc", "mnk"}),
    Serbia("Serbia", "RS", "SRB", "rs", "RI", 688, "381", "Europe", new String[]{"sr", "hu", "bs", "rom"}),
    Seychelles("Seychelles", "SC", "SYC", "sc", "SE", 690, "248", "Africa", new String[]{"en-SC", "fr-SC"}),
    SierraLeone("Sierra Leone", "SL", "SLE", "sl", "SL", 694, "232", "Africa", new String[]{"en-SL", "men", "tem"}),
    Singapore("Singapore", "SG", "SGP", "sg", "SN", 702, "65", "Asia",
            new String[]{"cmn", "en-SG", "ms-SG", "ta-SG", "zh-SG"}),
    SintMaarten("Sint Maarten", "SX", "SXM", "sx", "NN", 534, "1-721", "North America", new String[]{"nl", "en"}),
    Slovakia("Slovakia", "SK", "SVK", "sk", "LO", 703, "421", "Europe", new String[]{"sk", "hu"}),
    Slovenia("Slovenia", "SI", "SVN", "si", "SI", 705, "386", "Europe", new String[]{"sl", "sh"}),
    SolomonIslAnds("Solomon Islands", "SB", "SLB", "sb", "BP", 90, "677", "Oceania", new String[]{"en-SB", "tpi"}),
    Somalia("Somalia", "SO", "SOM", "so", "SO", 706, "252", "Africa", new String[]{"so-SO", "ar-SO", "it", "en-SO"}),
    SouthAfrica("South Africa", "ZA", "ZAF", "za", "SF", 710, "27", "Africa",
            new String[]{"zu", "xh", "af", "nso", "en-ZA", "tn", "st", "ts", "ss", "ve", "nr"}),
    SouthKorea("South Korea", "KR", "KOR", "kr", "KS", 410, "82", "Asia", new String[]{"ko-KR", "en"}),
    SouthSudan("South Sudan", "SS", "SSD", "ss", "OD", 728, "211", "Africa", new String[]{"en"}),
    Spain("Spain", "ES", "ESP", "es", "SP", 724, "34", "Europe", new String[]{"es-ES", "ca", "gl", "eu", "oc"}),
    SriLanka("Sri Lanka", "LK", "LKA", "lk", "CE", 144, "94", "Asia", new String[]{"si", "ta", "en"}),
    Sudan("Sudan", "SD", "SDN", "sd", "SU", 729, "249", "Africa", new String[]{"ar-SD", "en", "fia"}),
    Suriname("Suriname", "SR", "SUR", "sr", "NS", 740, "597", "South America",
            new String[]{"nl-SR", "en", "srn", "hns", "jv"}),
    SvalbardAndJanMayen("Svalbard and Jan Mayen", "SJ", "SJM", "sj", "SV", 744, "47", "Europe",
            new String[]{"no", "ru"}),
    SwazilAnd("Swaziland", "SZ", "SWZ", "sz", "WZ", 748, "268", "Africa", new String[]{"en-SZ", "ss-SZ"}),
    Sweden("Sweden", "SE", "SWE", "se", "SW", 752, "46", "Europe", new String[]{"sv-SE", "se", "sma", "fi-SE"}),
    SwitzerlAnd("Switzerland", "CH", "CHE", "ch", "SZ", 756, "41", "Europe",
            new String[]{"de-CH", "fr-CH", "it-CH", "rm"}),
    Syria("Syria", "SY", "SYR", "sy", "SY", 760, "963", "Asia",
            new String[]{"ar-SY", "ku", "hy", "arc", "fr", "en"}),
    Taiwan("Taiwan", "TW", "TWN", "tw", "TW", 158, "886", "Asia", new String[]{"zh-TW", "zh", "nan", "hak"}),
    Tajikistan("Tajikistan", "TJ", "TJK", "tj", "TI", 762, "992", "Asia", new String[]{"tg", "ru"}),
    Tanzania("Tanzania", "TZ", "TZA", "tz", "TZ", 834, "255", "Africa", new String[]{"sw-TZ", "en", "ar"}),
    ThailAnd("Thailand", "TH", "THA", "th", "TH", 764, "66", "Asia", new String[]{"th", "en"}),
    Togo("Togo", "TG", "TGO", "tg", "TO", 768, "228", "Africa",
            new String[]{"fr-TG", "ee", "hna", "kbp", "dag", "ha"}),
    Tokelau("Tokelau", "TK", "TKL", "tk", "TL", 772, "690", "Oceania", new String[]{"tkl", "en-TK"}),
    Tonga("Tonga", "TO", "TON", "to", "TN", 776, "676", "Oceania", new String[]{"to", "en-TO"}),
    TrinidadAndTobago("Trinidad and Tobago", "TT", "TTO", "tt", "TD", 780, "1-868", "North America",
            new String[]{"en-TT", "hns", "fr", "es", "zh"}),
    Tunisia("Tunisia", "TN", "TUN", "tn", "TS", 788, "216", "Africa", new String[]{"ar-TN", "fr"}),
    Turkey("Turkey", "TR", "TUR", "tr", "TU", 792, "90", "Asia", new String[]{"tr-TR", "ku", "diq", "az", "av"}),
    Turkmenistan("Turkmenistan", "TM", "TKM", "tm", "TX", 795, "993", "Asia", new String[]{"tk", "ru", "uz"}),
    TurksAndCaicosIslAnds("Turks and Caicos Islands", "TC", "TCA", "tc", "TK", 796, "1-649", "North America",
            new String[]{"en-TC"}),
    Tuvalu("Tuvalu", "TV", "TUV", "tv", "TV", 798, "688", "Oceania", new String[]{"tvl", "en", "sm", "gil"}),
    USVirginIslAnds("U.S. Virgin Islands", "VI", "VIR", "vi", "VQ", 850, "1-340", "North America",
            new String[]{"en-VI"}),
    UgAnda("Uganda", "UG", "UGA", "ug", "UG", 800, "256", "Africa", new String[]{"en-UG", "lg", "sw", "ar"}),
    Ukraine("Ukraine", "UA", "UKR", "ua", "UP", 804, "380", "Europe",
            new String[]{"uk", "ru-UA", "rom", "pl", "hu"}),
    UnitedArabEmirates("United Arab Emirates", "AE", "ARE", "ae", "AE", 784, "971", "Asia",
            new String[]{"ar-AE", "fa", "en", "hi", "ur"}),
    UnitedKingdom("United Kingdom", "GB", "GBR", "uk", "UK", 826, "44", "Europe",
            new String[]{"en-GB", "cy-GB", "gd"}),
    UnitedStates("United States", "US", "USA", "us", "US", 840, "1", "North America",
            new String[]{"en-US", "es-US", "haw", "fr"}),
    Uruguay("Uruguay", "UY", "URY", "uy", "UY", 858, "598", "South America", new String[]{"es-UY"}),
    Uzbekistan("Uzbekistan", "UZ", "UZB", "uz", "UZ", 860, "998", "Asia", new String[]{"uz", "ru", "tg"}),
    Vanuatu("Vanuatu", "VU", "VUT", "vu", "NH", 548, "678", "Oceania", new String[]{"bi", "en-VU", "fr-VU"}),
    Vatican("Vatican", "VA", "VAT", "va", "VT", 336, "379", "Europe", new String[]{"la", "it", "fr"}),
    Venezuela("Venezuela", "VE", "VEN", "ve", "VE", 862, "58", "South America", new String[]{"es-VE"}),
    Vietnam("Vietnam", "VN", "VNM", "vn", "VM", 704, "84", "Asia", new String[]{"vi", "en", "fr", "zh", "km"}),
    WallisAndFutuna("Wallis and Futuna", "WF", "WLF", "wf", "WF", 876, "681", "Oceania",
            new String[]{"wls", "fud", "fr-WF"}),
    WesternSahara("Western Sahara", "EH", "ESH", "eh", "WI", 732, "212", "Africa", new String[]{"ar", "mey"}),
    Yemen("Yemen", "YE", "YEM", "ye", "YM", 887, "967", "Asia", new String[]{"ar-YE"}),
    Zambia("Zambia", "ZM", "ZMB", "zm", "ZA", 894, "260", "Africa",
            new String[]{"en-ZM", "bem", "loz", "lun", "lue", "ny", "toi"}),
    Zimbabwe("Zimbabwe", "ZW", "ZWE", "zw", "ZI", 716, "263", "Africa", new String[]{"en-ZW", "sn", "nr", "nd"});
//@formatter:on

    /**
     * String containing the ISO2
     */
    private final String ISO2;

    /**
     * String containing the ISO3
     */
    private final String ISO3;

    /**
     * Enum of the Continent
     */
    private final ContinentEnum continent;

    /**
     * String containing the Country Name
     */
    private final String countryName;

    /**
     * The FIPS Country code
     */
    private final String fips;

    /**
     * Integer for the ISO Numeric Country Code
     */
    private final Integer iSONumeric;

    /**
     * String Array of the Language Codes for the locaiton
     */
    private final String[] languageCodes;

    /**
     * String Containing the International dialing prefix
     */
    private final String phoneCode;

    /**
     * String containing the top level Internet domain
     */
    private final String topLevelInternetDomain;

    Country(final String countryName, final String digram, final String trigram, final String topLevelInternetDomain,
            final String fips, final Integer iSONumeric, final String phoneCode, final String continent,
            final String[] languageCodes) {
        this.ISO2 = digram;
        this.ISO3 = trigram;
        this.continent = ContinentEnum.valueOf(continent);
        this.countryName = countryName;
        this.fips = fips;
        this.iSONumeric = iSONumeric;
        this.languageCodes = languageCodes;
        this.phoneCode = phoneCode;
        this.topLevelInternetDomain = topLevelInternetDomain;
    }

    @Override
    public ContinentEnum getContinent() {
        return this.continent;
    }

    @Override
    public Optional<String> getDisplayLanguage() {
        final Optional<String> iso2 = this.getISO2();
        if (iso2.isPresent()) {
            return Optional.ofNullable(new Locale("", iso2.get()).getDisplayLanguage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> getFips() {
        return Optional.ofNullable(this.fips);
    }

    @Override
    public Optional<String> getISO2() {
        return Optional.ofNullable(this.ISO2);
    }

    @Override
    public Optional<String> getISO3() {
        return Optional.ofNullable(this.ISO3);
    }

    @Override
    public Optional<Integer> getISONumeric() {
        return Optional.ofNullable(this.iSONumeric);
    }

    @Override
    public Optional<String[]> getLanguageCodes() {
        return Optional.ofNullable(this.languageCodes);
    }

    @Override
    public String getName() {
        return this.countryName;
    }

    @Override
    public Optional<String> getPhoneCode() {
        return Optional.ofNullable(this.phoneCode);
    }

    @Override
    public Optional<String> getTopLevelInternetDomain() {
        return Optional.ofNullable(this.topLevelInternetDomain);
    }
}
