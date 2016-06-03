/**
Open Bank Project - API
Copyright (C) 2011-2015, TESOBE Ltd

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

Email: contact@tesobe.com
TESOBE / Music Pictures Ltd
Osloerstrasse 16/17
Berlin 13359, Germany

  This product includes software developed at
  TESOBE (http://www.tesobe.com/)
  by
  Simon Redfern : simon AT tesobe DOT com
  Stefan Bethge : stefan AT tesobe DOT com
  Everett Sochowski : everett AT tesobe DOT com
  Ayoub Benali: ayoub AT tesobe DOT com

 */
package code.api.v2_0_0

import java.net.URL
import java.util.Date

import code.TransactionTypes.TransactionType.TransactionType
import code.meetings.Meeting
import code.model.dataAccess.OBPUser
import code.transactionrequests.TransactionRequests._
import net.liftweb.common.{Full, Box}

// import code.api.util.APIUtil.ApiLink

import code.api.v1_2_1.{AmountOfMoneyJSON, JSONFactory => JSONFactory121, MinimalBankJSON => MinimalBankJSON121, OtherAccountJSON => OtherAccountJSON121, ThisAccountJSON => ThisAccountJSON121, TransactionDetailsJSON => TransactionDetailsJSON121, UserJSON => UserJSON121, ViewJSON => ViewJSON121}
import code.api.v1_4_0.JSONFactory1_4_0.{CustomerFaceImageJson, ChallengeJSON, TransactionRequestAccountJSON}
import code.kycchecks.KycCheck
import code.kycdocuments.KycDocument
import code.kycmedias.KycMedia
import code.kycstatuses.KycStatus
import code.model._
import code.socialmedia.SocialMedia
import net.liftweb.json.JsonAST.JValue



// New in 2.0.0

class LinkJSON(
  val href: URL,
  val rel: String,
  val method: String
)

class LinksJSON(
  val _links: List[LinkJSON]
)

class ResultAndLinksJSON(
  val result : JValue,
  val links: LinksJSON
)

case class CreateUserJSON(
                     email: String,
                     password: String,
                     first_name: String,
                     last_name: String
                   )


case class CreateMeetingJSON(
                              provider_id: String,
                              purpose_id: String
)

case class MeetingJSON(
                        meeting_id : String,
                        provider_id: String,
                        purpose_id: String,
                        bank_id : String,
                        present : MeetingPresentJSON,
                        keys : MeetingKeysJSON,
                        when : Date
                      )

case class MeetingJSONs(
                        meetings : List[MeetingJSON]
                      )


case class MeetingKeysJSON(
                            session_id: String,
                            staff_token: String,
                            customer_token: String
                         )

case class MeetingPresentJSON(
                               staff_user_id: String,
                               customer_user_id: String

  )

case class UserCustomerLinkJSON(customer_id: String,
                                user_id: String,
                                bank_id: String,
                                date_inserted: Date,
                                is_active: Boolean)
case class UserCustomerLinkJSONs(l: List[UserCustomerLinkJSON])

class BasicViewJSON(
  val id: String,
  val short_name: String,
  val is_public: Boolean
)

case class BasicAccountsJSON(
  accounts : List[BasicAccountJSON]
)

// Basic Account has basic View
case class BasicAccountJSON(
                             id : String,
                             label : String,
                             views_available : List[BasicViewJSON],
                             bank_id : String
)


// Json used in account creation
case class CreateAccountJSON(
                             `type` : String,
                             balance : AmountOfMoneyJSON
                           )

// No view in core
case class CoreAccountJSON(
                             id : String,
                             label : String,
                             bank_id : String,
                             _links: JValue
                           )

case class KycDocumentJSON(
  id: String,
  customer_number: String,
  `type`: String,
  number: String,
  issue_date: Date,
  issue_place: String,
  expiry_date: Date
)
case class KycDocumentsJSON(documents: List[KycDocumentJSON])

case class KycMediaJSON(
  id: String,
  customer_number: String,
  `type`: String,
  url: String,
  date: Date,
  relates_to_kyc_document_id: String,
  relates_to_kyc_check_id: String
)
case class KycMediasJSON(medias: List[KycMediaJSON])

case class KycCheckJSON(
  id: String,
  customer_number: String,
  date: Date,
  how: String,
  staff_user_id: String,
  staff_name: String,
  satisfied: Boolean,
  comments: String
)
case class KycChecksJSON(checks: List[KycCheckJSON])

case class KycStatusJSON(
   customer_number: String,
   ok: Boolean,
   date: Date
)
case class KycStatusesJSON(statuses: List[KycStatusJSON])

case class SocialMediaJSON(
   customer_number: String,
   `type`: String,
   handle: String,
   date_added: Date,
   date_activated: Date
)
case class SocialMediasJSON(checks: List[SocialMediaJSON])

case class CreateCustomerJson(
                             user_id: String,
                             customer_number : String,
                             legal_name : String,
                             mobile_phone_number : String,
                             email : String,
                             face_image : CustomerFaceImageJson,
                             date_of_birth: Date,
                             relationship_status: String,
                             dependants: Int,
                             dob_of_dependants: List[Date],
                             highest_education_attained: String,
                             employment_status: String,
                             kyc_status: Boolean,
                             last_ok_date: Date)




// TODO Use the scala doc of a case class in the Resource Doc if a case class is given as a return type


/** A TransactionType categorises a transaction on a bank statement.
  *
  * i.e. it justifies the reason for a transaction on a bank statement to exist
  * e.g. a bill-payment, ATM-withdrawal, interest-payment or some kind of fee to the customer.
  *
  * This is the JSON respresentation (v2.0.0) of the object
  *
  * @param id Unique id across the API instance. Ideally a UUID
  * @param bank_id The bank that supports this TransactionType
  * @param short_code A short code (ideally-no-spaces) which is unique across the bank. Should map to transaction.details.types
  * @param summary A succinct summary
  * @param description A longer description
  * @param charge The fee to the customer for each one of these
  */

case class TransactionTypeJSON (
                                 id: TransactionTypeId,
                                 bank_id : String,
                                 short_code : String,
                                 summary: String,
                                 description: String,
                                 charge: AmountOfMoneyJSON
 )



case class TransactionTypesJSON(transaction_types: List[TransactionTypeJSON])



/*
v2.0.0 Json Representation of TransactionRequest
 */


case class TransactionRequestChargeJSON(
                                     val summary: String,
                                     val value : AmountOfMoneyJSON
                                   )


case class TransactionRequestJSON(
                                        id: String,
                                        `type`: String,
                                        from: TransactionRequestAccountJSON,
                                        body: TransactionRequestBodyJSON,
                                        transaction_ids: String,
                                        status: String,
                                        start_date: Date,
                                        end_date: Date,
                                        challenge: ChallengeJSON
                                      )


case class TransactionRequestWithChargeJSON(
                                   id: String,
                                   `type`: String,
                                   from: TransactionRequestAccountJSON,
                                   body: TransactionRequestBodyJSON,
                                   transaction_ids: String,
                                   status: String,
                                   start_date: Date,
                                   end_date: Date,
                                   challenge: ChallengeJSON,
                                   charge : TransactionRequestChargeJSON
                                 )





case class TransactionRequestWithChargeJSONs(
                                    transaction_requests_with_charges : List[TransactionRequestWithChargeJSON]
                                 )







case class TransactionRequestBodyJSON (
                                        to: TransactionRequestAccountJSON,
                                        value : AmountOfMoneyJSON,
                                        description : String
                                      )


object JSONFactory200{

  // Modified in 2.0.0

  //transaction requests
  def getTransactionRequestBodyFromJson(body: TransactionRequestBodyJSON) : TransactionRequestBody = {
    val toAcc = TransactionRequestAccount (
      bank_id = body.to.bank_id,
      account_id = body.to.account_id
    )
    val amount = AmountOfMoney (
      currency = body.value.currency,
      amount = body.value.amount
    )

    TransactionRequestBody (
      to = toAcc,
      value = amount,
      description = body.description
    )
  }

  def getTransactionRequestFromJson(json : TransactionRequestJSON) : TransactionRequest = {
    val fromAcc = TransactionRequestAccount (
      json.from.bank_id,
      json.from.account_id
    )
    val challenge = TransactionRequestChallenge (
      id = json.challenge.id,
      allowed_attempts = json.challenge.allowed_attempts,
      challenge_type = json.challenge.challenge_type
    )

    val charge = TransactionRequestCharge("Total charges for a completed transaction request.", AmountOfMoney(json.body.value.currency, "0.05"))


    TransactionRequest (
      id = TransactionRequestId(json.id),
      `type`= json.`type`,
      from = fromAcc,
      body = getTransactionRequestBodyFromJson(json.body),
      transaction_ids = json.transaction_ids,
      status = json.status,
      start_date = json.start_date,
      end_date = json.end_date,
      challenge = challenge,
      charge = charge
    )
  }









  // New in 2.0.0


  def createViewBasicJSON(view : View) : BasicViewJSON = {
    val alias =
      if(view.usePublicAliasIfOneExists)
        "public"
      else if(view.usePrivateAliasIfOneExists)
        "private"
      else
        ""

    new BasicViewJSON(
      id = view.viewId.value,
      short_name = stringOrNull(view.name),
      is_public = view.isPublic
    )
  }


  def createBasicAccountJSON(account : BankAccount, basicViewsAvailable : List[BasicViewJSON] ) : BasicAccountJSON = {
    new BasicAccountJSON(
      account.accountId.value,
      stringOrNull(account.label),
      basicViewsAvailable,
      account.bankId.value
    )
  }

  // Contains only minimal info (could have more if owner) plus links
  def createCoreAccountJSON(account : BankAccount, links: JValue ) : CoreAccountJSON = {
    val coreAccountJson = new CoreAccountJSON(
      account.accountId.value,
      stringOrNull(account.label),
      account.bankId.value,
      links
    )
    coreAccountJson
  }


  case class ModeratedCoreAccountJSON(
                                   id : String,
                                   label : String,
                                   number : String,
                                   owners : List[UserJSON121],
                                   `type` : String,
                                   balance : AmountOfMoneyJSON,
                                   IBAN : String,
                                   swift_bic: String,
                                   bank_id : String
                                 )

  case class CoreTransactionsJSON(
                               transactions: List[CoreTransactionJSON]
                             )

  case class CoreTransactionJSON(
                              id : String,
                              account : ThisAccountJSON121,
                              counterparty : CoreCounterpartyJSON,
                              details : CoreTransactionDetailsJSON
                            )



  case class CoreAccountHolderJSON(
                                name : String
                              )


  case class CoreCounterpartyJSON(
                                   id : String,
                                   holder : CoreAccountHolderJSON,
                                   number : String,
                                   kind : String,
                                   IBAN : String,
                                   swift_bic: String,
                                   bank : MinimalBankJSON121
                             )




  def createCoreTransactionsJSON(transactions: List[ModeratedTransaction]) : CoreTransactionsJSON = {
    new CoreTransactionsJSON(transactions.map(createCoreTransactionJSON))
  }

  case class CoreTransactionDetailsJSON(
                                     `type` : String,
                                     description : String,
                                     posted : Date,
                                     completed : Date,
                                     new_balance : AmountOfMoneyJSON,
                                     value : AmountOfMoneyJSON
                                   )



  //
  case class UserJSON(
                       user_id: String,
                       email : String,
                       provider_id: String,
                       provider : String,
                       display_name : String
                     )





  def createUserJSONfromOBPUser(user : OBPUser) : UserJSON = new UserJSON(
    user_id = user.user.foreign.get.userId,
    email = user.email,
    provider_id = stringOrNull(user.provider),
    provider = stringOrNull(user.provider),
    display_name = stringOrNull(user.displayName())
  )


  def createUserJSON(user : User) : UserJSON = {
    new UserJSON(
      user_id = user.userId,
      email = user.emailAddress,
      provider_id = user.idGivenByProvider,
      provider = stringOrNull(user.provider),
      display_name = stringOrNull(user.name) //TODO: Rename to displayName ?
    )
  }

  def createUserJSON(user : Box[User]) : UserJSON = {
    user match {
      case Full(u) => createUserJSON(u)
      case _ => null
    }
  }



  def createUserJSONfromOBPUser(user : Box[OBPUser]) : UserJSON = {
    user match {
      case Full(u) => createUserJSONfromOBPUser(u)
      case _ => null
    }
  }





  def createCoreTransactionDetailsJSON(transaction : ModeratedTransaction) : CoreTransactionDetailsJSON = {
    new CoreTransactionDetailsJSON(
      `type` = stringOptionOrNull(transaction.transactionType),
      description = stringOptionOrNull(transaction.description),
      posted = transaction.startDate.getOrElse(null),
      completed = transaction.finishDate.getOrElse(null),
      new_balance = JSONFactory121.createAmountOfMoneyJSON(transaction.currency, transaction.balance),
      value= JSONFactory121.createAmountOfMoneyJSON(transaction.currency, transaction.amount.map(_.toString))
    )
  }


  def createCoreTransactionJSON(transaction : ModeratedTransaction) : CoreTransactionJSON = {
    new CoreTransactionJSON(
      id = transaction.id.value,
      account = transaction.bankAccount.map(JSONFactory121.createThisAccountJSON).getOrElse(null),
      counterparty = transaction.otherBankAccount.map(createCoreCounterparty).getOrElse(null),
      details = createCoreTransactionDetailsJSON(transaction)
    )
  }





  case class CounterpartiesJSON(
                                 counterparties : List[CoreCounterpartyJSON]
                              )


  def createCoreCounterparty(bankAccount : ModeratedOtherBankAccount) : CoreCounterpartyJSON = {
    new CoreCounterpartyJSON(
      id = bankAccount.id,
      number = stringOptionOrNull(bankAccount.number),
      kind = stringOptionOrNull(bankAccount.kind),
      IBAN = stringOptionOrNull(bankAccount.iban),
      swift_bic = stringOptionOrNull(bankAccount.swift_bic),
      bank = JSONFactory121.createMinimalBankJSON(bankAccount),
      holder = createAccountHolderJSON(bankAccount.label.display, bankAccount.isAlias)
    )
  }



  def createAccountHolderJSON(owner : User, isAlias : Boolean) : CoreAccountHolderJSON = {
    // Note we are not using isAlias
    new CoreAccountHolderJSON(
      name = owner.name
    )
  }

  def createAccountHolderJSON(name : String, isAlias : Boolean) : CoreAccountHolderJSON = {
    // Note we are not using isAlias
    new CoreAccountHolderJSON(
      name = name
    )
  }



  def createCoreBankAccountJSON(account : ModeratedBankAccount, viewsAvailable : List[ViewJSON121]) : ModeratedCoreAccountJSON =  {
    val bankName = account.bankName.getOrElse("")
    new ModeratedCoreAccountJSON (
      account.accountId.value,
      JSONFactory121.stringOptionOrNull(account.label),
      JSONFactory121.stringOptionOrNull(account.number),
      JSONFactory121.createOwnersJSON(account.owners.getOrElse(Set()), bankName),
      JSONFactory121.stringOptionOrNull(account.accountType),
      JSONFactory121.createAmountOfMoneyJSON(account.currency.getOrElse(""), account.balance),
      JSONFactory121.stringOptionOrNull(account.iban),
      JSONFactory121.stringOptionOrNull(account.swift_bic),
      stringOrNull(account.bankId.value)
    )
  }






  def createKycDocumentJSON(kycDocument : KycDocument) : KycDocumentJSON = {
    new KycDocumentJSON(
      id = kycDocument.idKycDocument,
      customer_number = kycDocument.customerNumber,
      `type` = kycDocument.`type`,
      number = kycDocument.number,
      issue_date = kycDocument.issueDate,
      issue_place = kycDocument.issuePlace,
      expiry_date = kycDocument.expiryDate
    )
  }

  def createKycDocumentsJSON(messages : List[KycDocument]) : KycDocumentsJSON = {
    KycDocumentsJSON(messages.map(createKycDocumentJSON))
  }

  def createKycMediaJSON(kycMedia : KycMedia) : KycMediaJSON = {
    new KycMediaJSON(
      id = kycMedia.idKycMedia,
      customer_number = kycMedia.customerNumber,
      `type` = kycMedia.`type`,
      url = kycMedia.url,
      date = kycMedia.date,
      relates_to_kyc_document_id = kycMedia.relatesToKycDocumentId,
      relates_to_kyc_check_id = kycMedia.relatesToKycCheckId
    )
  }
  def createKycMediasJSON(messages : List[KycMedia]) : KycMediasJSON = {
    KycMediasJSON(messages.map(createKycMediaJSON))
  }

  def createKycCheckJSON(kycCheck : KycCheck) : KycCheckJSON = {
    new KycCheckJSON(
      id = kycCheck.idKycCheck,
      customer_number = kycCheck.customerNumber,
      date = kycCheck.date,
      how = kycCheck.how,
      staff_user_id = kycCheck.staffUserId,
      staff_name = kycCheck.staffName,
      satisfied = kycCheck.satisfied,
      comments = kycCheck.comments
    )
  }
  def createKycChecksJSON(messages : List[KycCheck]) : KycChecksJSON = {
    KycChecksJSON(messages.map(createKycCheckJSON))
  }

  def createKycStatusJSON(kycStatus : KycStatus) : KycStatusJSON = {
    new KycStatusJSON(
      customer_number = kycStatus.customerNumber,
      ok = kycStatus.ok,
      date = kycStatus.date
    )
  }
  def createKycStatusesJSON(messages : List[KycStatus]) : KycStatusesJSON = {
    KycStatusesJSON(messages.map(createKycStatusJSON))
  }

  def createSocialMediaJSON(socialMedia : SocialMedia) : SocialMediaJSON = {
    new SocialMediaJSON(
      customer_number = socialMedia.customerNumber,
      `type` = socialMedia.`type`,
      handle = socialMedia.handle,
      date_added = socialMedia.dateAdded,
      date_activated = socialMedia.dateActivated
    )
  }
  def createSocialMediasJSON(messages : List[SocialMedia]) : SocialMediasJSON = {
    SocialMediasJSON(messages.map(createSocialMediaJSON))
  }


  /** Creates v2.0.0 representation of a TransactionType
    *
    *
    * @param transactionType An internal TransactionType instance
    * @return a v2.0.0 representation of a TransactionType
    */

def createTransactionTypeJSON(transactionType : TransactionType) : TransactionTypeJSON = {
    new TransactionTypeJSON(
      id = transactionType.id,
      bank_id = transactionType.bankId.toString,
      short_code = transactionType.shortCode,
      summary = transactionType.summary,
      description = transactionType.description,
      charge = new AmountOfMoneyJSON(currency = transactionType.charge.currency, amount = transactionType.charge.amount)
    )
  }
  def createTransactionTypeJSON(transactionTypes : List[TransactionType]) : TransactionTypesJSON = {
    TransactionTypesJSON(transactionTypes.map(createTransactionTypeJSON))
  }




  /** Creates v2.0.0 representation of a TransactionType
    *
    *
    * @param tr An internal TransactionRequest instance
    * @return a v2.0.0 representation of a TransactionRequest
    */

  def createTransactionRequestWithChargeJSON(tr : TransactionRequest) : TransactionRequestWithChargeJSON = {
    new TransactionRequestWithChargeJSON(
      id = tr.id.value,
      `type` = tr.`type`,
      from = TransactionRequestAccountJSON (
        bank_id = tr.from.bank_id,
        account_id = tr.from.account_id),
      body =  TransactionRequestBodyJSON (
          to = TransactionRequestAccountJSON (
            bank_id = tr.body.to.bank_id,
            account_id = tr.body.to.account_id),
          value = AmountOfMoneyJSON (currency = tr.body.value.currency, amount = tr.body.value.amount),
          description = tr.body.description),
      transaction_ids = tr.transaction_ids,
      status = tr.status,
      start_date = tr.start_date,
      end_date = tr.end_date,
      // Some (mapped) data might not have the challenge. TODO Make this nicer
      challenge = {
        try {ChallengeJSON (id = tr.challenge.id, allowed_attempts = tr.challenge.allowed_attempts, challenge_type = tr.challenge.challenge_type)}
        // catch { case _ : Throwable => ChallengeJSON (id = "", allowed_attempts = 0, challenge_type = "")}
        catch { case _ : Throwable => null}
      },
      charge = TransactionRequestChargeJSON (summary = tr.charge.summary,
                                              value = AmountOfMoneyJSON(currency = tr.charge.value.currency,
                                                                        amount = tr.charge.value.amount)
      )
    )
  }
  def createTransactionRequestJSONs(trs : List[TransactionRequest]) : TransactionRequestWithChargeJSONs = {
    TransactionRequestWithChargeJSONs(trs.map(createTransactionRequestWithChargeJSON))
  }

  def createMeetingJSON(meeting : Meeting) : MeetingJSON = {
    MeetingJSON(meeting_id = meeting.meetingId,
                provider_id = meeting.providerId,
                purpose_id = meeting.purposeId,
                bank_id = meeting.bankId,
                present = MeetingPresentJSON(staff_user_id = meeting.present.staffUserId,
                                              customer_user_id = meeting.present.customerUserId),
                keys = MeetingKeysJSON(session_id = meeting.keys.sessionId,
                                        staff_token = meeting.keys.staffToken,
                                        customer_token = meeting.keys.customerToken),
                when = meeting.when)

  }

  def createMeetingJSONs(meetings : List[Meeting]) : MeetingJSONs = {
    MeetingJSONs(meetings.map(createMeetingJSON))
  }

  def createUserCustomerLinkJSON(ucl: code.usercustomerlinks.UserCustomerLink) = {
    UserCustomerLinkJSON(customer_id = ucl.customerId,
      user_id = ucl.userId,
      bank_id = ucl.bankId,
      date_inserted = ucl.dateInserted,
      is_active = ucl.isActive
    )
  }

  def createUserCustomerLinkJSONs(ucls: List[code.usercustomerlinks.UserCustomerLink]): UserCustomerLinkJSONs = {
    UserCustomerLinkJSONs(ucls.map(createUserCustomerLinkJSON))
  }

  // Copied from 1.2.1 (import just this def instead?)
  def stringOrNull(text : String) =
    if(text == null || text.isEmpty)
      null
    else
      text

  // Copied from 1.2.1 (import just this def instead?)
  def stringOptionOrNull(text : Option[String]) =
    text match {
      case Some(t) => stringOrNull(t)
      case _ => null
    }






}