<h1> CryptoTradeSimulator </h1>

<p>A one-off IDE-Backtesting Program used to test the effectiveness of Automated Scalping on the Cryptocurrency Market</p>

<h3> Summary </h3>

<p>Used data from a small amount of crypto echanges on five of the largest currencies in an hour-by-hour format to experiment and quantify the effectiveness of a common trading strategy called 'scalping' in the realm of risk management when used by a bot.</p>

<h3> Results </h3> 

<p> Scapling proved to be a poor overall contribution to investments. It reduced loss by a minimal margin (maximum shown was less than a 7% reduction) while drastically reducing profit, especially in a bull market (when tested using data from the bubble in Dec. 2017, up to 50% reduction in gains was demonstrated). Results show that scalping using this method in an automated setting does not work to reduce risk. Later, walkforward testing (and live testing) proved many other unforseen issues </p>

<h3>Potential Issues</h3>

<p> The methodology of this experiment was quite strange. Due to the amount of entries (and quickly and rather sloppily written algorithm), sample times were small (2-8 weeks). This meant arbitrary samples had to be chosen to attempt to acquire fair and accurate results. This arbitrary selection could potentially have missed a sample with more positive results. </p>

<p> As mentioned in the summary section, walkforward and live testing (a.k.a., losing money in the name of science) proved many issues that worsened that results. For one, the brokerage used, Coinbase Pro, has very high fees compared to the competiton at 0.5% of the sale price. Additionally, each cryptocurrency has a "minimum sell value," some zero or negative power of 10, limited the size of the scalps allowed. Due to the high risk, a very small amount of initial capital was used ($200), so this limit was extremely noticible, especially on cryptocurrencies like Bitcoin and Ethereum, which had a minimum sell value at $9 and $2.5 respectively.</p>

<h3> Summary </h3>
<ol>
<li>Algorithmic scalping in the particular strategy used proved to be unprofitable in a short term setting</li>
  <li> Using real money after failed backtests and walkforward tests is a bad idea </li>
</ol>
