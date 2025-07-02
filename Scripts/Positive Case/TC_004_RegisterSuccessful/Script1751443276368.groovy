import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.util.KeywordUtil
import groovy.json.JsonSlurper

KeywordUtil.logInfo("Memulai Tes: POST Register Successful dengan Verifikasi Lengkap")

'Siapkan Data yang Akan Dikirim'
//Sesuai dokumentasi reqres.in, hanya email 'eve.holt@reqres.in' yang akan memberikan respons sukses.
def emailToSend = "eve.holt@reqres.in"
def passwordToSend = "pistol" // Password bisa apa saja
KeywordUtil.logInfo("Data yang akan dikirim: Email='${emailToSend}'")

'Kirim Request dengan Data Dinamis'
def response = WS.sendRequest(findTestObject('Object Repository/Web Service/Reqresin/POST_register_succesfull', [
	('userEmail')    : emailToSend,
	('userPassword') : passwordToSend
]))

'Check Status & Header'
KeywordUtil.logInfo("Memverifikasi status code dan header...")
WS.verifyResponseStatusCode(response, 200)
String contentTypeHeader = response.getHeaderField('Content-Type')
assert contentTypeHeader.contains('application/json')
KeywordUtil.logInfo("✔️ Status Code dan Content-Type sudah sesuai.")

'Parse JSON'
def jsonSlurper = new JsonSlurper()
def parsedResponse = jsonSlurper.parseText(response.getResponseText())

'Verifikasi keberadaan field'
assert parsedResponse.containsKey('id')
assert parsedResponse.containsKey('token')

'Verifikasi nilai dan tipe data'
assert parsedResponse.id == 4 : "ID yang dikembalikan tidak sesuai. Diharapkan 4 untuk user 'eve.holt@reqres.in'."
assert parsedResponse.id instanceof Integer : "Tipe data ID harus Integer"
assert parsedResponse.token instanceof String && !(parsedResponse.token.isEmpty()) : "Token harus berupa String dan tidak boleh kosong"

KeywordUtil.logInfo("✔️ ID dan Token telah terverifikasi dengan benar.")
KeywordUtil.logInfo("    User ID: ${parsedResponse.id}")
KeywordUtil.logInfo("    User Token: ${parsedResponse.token}")

KeywordUtil.logInfo("Tes Selesai: Verifikasi POST Register Successful berhasil.")