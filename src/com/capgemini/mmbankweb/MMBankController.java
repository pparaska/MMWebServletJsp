package com.capgemini.mmbankweb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.account.service.SavingsAccountService;
import com.moneymoney.account.service.SavingsAccountServiceImpl;
import com.moneymoney.account.util.DBUtil;
import com.moneymoney.exception.AccountNotFoundException;

@WebServlet("*.mm")
public class MMBankController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int flagSortName = 1;
	int flagSortByNumber = 1;
	int flagSortBySalaried = 1;
	int flagSortByBalance = 1;

	private SavingsAccountServiceImpl savingsAccountService;
	private RequestDispatcher dispatcher;

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/bankapp_db", "root", "root");
			PreparedStatement preparedStatement = connection
					.prepareStatement("DELETE FROM ACCOUNT");
		//	preparedStatement.execute();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String path = request.getServletPath();
		SavingsAccountService savingsAccountService = new SavingsAccountServiceImpl();
		SavingsAccount savingsAccount = null, sendersSavingsAccount = null, receiversSavingsAccount = null;
		PrintWriter out = response.getWriter();

		switch (path) {

		case "/addNewSA.mm":
			response.sendRedirect("addNewSAForm.jsp");
			break;
		case "/addNew.mm":
			String accountHolderName = request.getParameter("txtAccHN");
			double accountBalance = Double.parseDouble(request
					.getParameter("txtBalance"));
			boolean salary = request.getParameter("rdSalary").equalsIgnoreCase(
					"yes") ? true : false;

			try {
				savingsAccountService.createNewAccount(accountHolderName,
						accountBalance, salary);
				response.sendRedirect("getAll.mm");
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

			break;
		case "/searchForm.mm":
			response.sendRedirect("SearchForm.jsp");
			break;
		case "/search.mm":
			int accountNumber = Integer.parseInt(request
					.getParameter("txtAccountNumber"));
			try {
				SavingsAccount account = savingsAccountService
						.getAccountById(accountNumber);
				request.setAttribute("account", account);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;

		case "/searchFormByName.mm":
			response.sendRedirect("searchByName.jsp");
			break;

		case "/searchByNameForm.mm":
			String accountHolderName1 = request.getParameter("txtAccHN");
			try {
				SavingsAccount account = savingsAccountService
						.getAccountByName(accountHolderName1);
				request.setAttribute("account", account);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;

		case "/searchFormByBalance.mm":
			response.sendRedirect("searchByBalance.jsp");
			break;

		case "/searchByBalance.mm":
			double minimumBalance = Double.parseDouble(request
					.getParameter("txtMinBalance"));
			double maximumBalance = Double.parseDouble(request
					.getParameter("txtMaxBalance"));
			System.out.println(minimumBalance + "\t" + maximumBalance);
			try {
				List<SavingsAccount> account = savingsAccountService
						.getAccountByBalance(minimumBalance, maximumBalance);
				request.setAttribute("accounts", account);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;

		case "/getAll.mm":
			try {
				List<SavingsAccount> accounts = savingsAccountService
						.getAllSavingsAccount();
				request.setAttribute("accounts", accounts);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;

		case "/sortByName.mm":
			int flag = flagSortName;
			try {
				Collection<SavingsAccount> accounts = savingsAccountService
						.getAllSavingsAccount();
				Set<SavingsAccount> accountSet = new TreeSet<>(
						new Comparator<SavingsAccount>() {
							public int compare(SavingsAccount arg0,
									SavingsAccount arg1) {
								return flag
										* arg0.getBankAccount()
												.getAccountHolderName()
												.compareTo(
														arg1.getBankAccount()
																.getAccountHolderName());
							}
						});
				accountSet.addAll(accounts);
				if (flagSortName == 1) {
					flagSortName = -1;
				} else {
					flagSortName = 1;
				}
				request.setAttribute("accounts", accountSet);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);

			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

			break;

		case "/sortByAccountNumber.mm":
			int flag1 = flagSortByNumber;
			try {
				List<SavingsAccount> accounts = savingsAccountService
						.getAllSavingsAccount();
				Collections.sort(accounts, new Comparator<SavingsAccount>() {
					@Override
					public int compare(SavingsAccount arg0, SavingsAccount arg1) {
						return flag1
								* (arg0.getBankAccount().getAccountNumber() - arg1
										.getBankAccount().getAccountNumber());
					}
				});
				if (flagSortByNumber == 1) {
					flagSortByNumber = -1;
				} else {
					flagSortByNumber = 1;
				}
				request.setAttribute("accounts", accounts);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

			break;

		case "/sortBySalary.mm":
			int flag3 = flagSortBySalaried;
			try {
				Collection<SavingsAccount> accounts = savingsAccountService
						.getAllSavingsAccount();
				Set<SavingsAccount> accountSet = new TreeSet<>(
						new Comparator<SavingsAccount>() {
							@Override
							public int compare(SavingsAccount arg0,
									SavingsAccount arg1) {
								if (arg0.isSalary())
									return flag3 * -1;
								else
									return flag3 * +1;
							}
						});
				if (flagSortBySalaried == 1) {
					flagSortBySalaried = -1;
				} else {
					flagSortBySalaried = 1;
				}
				accountSet.addAll(accounts);
				request.setAttribute("accounts", accountSet);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

			break;

		case "/sortByBalance.mm":
			int flag4 = flagSortByBalance;

			try {

				List<SavingsAccount> al = savingsAccountService
						.getAllSavingsAccount();
				Collections.sort(al, new Comparator<SavingsAccount>() {
					@Override
					public int compare(SavingsAccount arg0, SavingsAccount arg1) {
						return (int) ((int) (flag4) * (arg0.getBankAccount()
								.getAccountBalance() - arg1.getBankAccount()
								.getAccountBalance()));
					}
				});
				if (flagSortByBalance == 1) {
					flagSortByBalance = -1;
				} else {
					flagSortByBalance = 1;
				}
				request.setAttribute("accounts", al);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

			break;
		case "/closeAccount.mm":
			response.sendRedirect("closeAccount.jsp");
			break;

		case "/closeAccountForm.mm":
			int accountid = Integer.parseInt(request
					.getParameter("accountNumber"));
			try {
				savingsAccountService.deleteAccount(accountid);
				response.sendRedirect("getAll.mm");
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;

		case "/withdraw.mm":
			response.sendRedirect("withdrawForm.jsp");
			break;
		case "/withdrawlForm.mm":
			int accountnumber = Integer.parseInt(request.getParameter("accId"));
			double amountWithdraw = Double.parseDouble(request
					.getParameter("amount"));
			try {
				savingsAccount = savingsAccountService
						.getAccountById(accountnumber);
				response.sendRedirect("getAll.mm");
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e1) {
				e1.printStackTrace();
			}
			try {
				savingsAccountService.withdraw(savingsAccount, amountWithdraw);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			try {
				DBUtil.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/deposit.mm":
			response.sendRedirect("depositForm.jsp");
			break;
		case "/depositForm.mm":
			int depositAccountNumber = Integer.parseInt(request
					.getParameter("accId"));
			double depositAmount = Double.parseDouble(request
					.getParameter("amount"));
			try {
				savingsAccount = savingsAccountService
						.getAccountById(depositAccountNumber);
				response.sendRedirect("getAll.mm");
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e1) {
				e1.printStackTrace();
			}
			try {
				savingsAccountService.deposit(savingsAccount, depositAmount);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			try {
				DBUtil.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/fundTansfer.mm":
			response.sendRedirect("fundTransfer.jsp");
			break;

		case "/fundTransfer.mm":
			int sendersAccountNumber = Integer.parseInt(request
					.getParameter("sendersAccId"));
			int receiversAccountNumber = Integer.parseInt(request
					.getParameter("receiversAccId"));
			double transferAmount = Double.parseDouble(request
					.getParameter("amount"));

			try {
				sendersSavingsAccount = savingsAccountService
						.getAccountById(sendersAccountNumber);
				response.sendRedirect("getAll.mm");
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				e.printStackTrace();
			}
			try {
				receiversSavingsAccount = savingsAccountService
						.getAccountById(receiversAccountNumber);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				e.printStackTrace();
			}
			try {
				savingsAccountService.fundTransfer(sendersSavingsAccount,
						receiversSavingsAccount, transferAmount);

			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

			break;

		case "/checkcurrentBalance.mm":
			response.sendRedirect("currentBalance.jsp");
			break;

		case "/currentBalance.mm":
			int accountnum = Integer.parseInt(request
					.getParameter("currentBal"));
			try {
				double currentBalance = savingsAccountService
						.checkAccountBalance(accountnum);
				out.println("Your Current Balance is :" + currentBalance);
				//response.sendRedirect("getAll.mm");
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				e.printStackTrace();
			}

			break;

		case "/updateAcc.mm":
			response.sendRedirect("UpdateAccount.html");
			break;

		case "/update.mm":
			int accountBal = Integer.parseInt(request
					.getParameter("currentBal"));
			try {
				SavingsAccount accountUpdate = savingsAccountService
						.getAccountById(accountBal);
				request.setAttribute("accounts", accountUpdate);
				dispatcher = request.getRequestDispatcher("UpdateDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;

		case "/updateAccount.mm":
			int accountId = Integer.parseInt(request.getParameter("txtNum"));
			SavingsAccount accountUpdate;
			try {
				accountUpdate = savingsAccountService.getAccountById(accountId);
				String accHName = request.getParameter("txtAccHn");
				accountUpdate.getBankAccount().setAccountHolderName(accHName);
				double accBal = Double.parseDouble(request
						.getParameter("txtBal"));
				boolean salaried = request.getParameter("rdSal")
						.equalsIgnoreCase("no") ? false : true;
				accountUpdate.setSalary(salaried);
				savingsAccountService.updateAccount(accountUpdate);
				response.sendRedirect("getAll.mm");
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}