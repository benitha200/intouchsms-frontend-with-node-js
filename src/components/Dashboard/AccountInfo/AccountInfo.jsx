import React from 'react'
import { Link, Outlet } from 'react-router-dom'


const AccountInfo = ({token}) => {
    return (
        <div className='pt-3'>
            <ul className="nav nav-tabs pt-4">
                <li className="nav-item">
                    <Link to="/account-info/" className="nav-link active">Profile</Link>
                </li>
                <li className="nav-item">
                    <Link to="/account-info/my-clients/" className="nav-link active">My Clients</Link>
                </li>
                <li className="nav-item">
                    <Link to="/account-info/my-credit-transfers/" className="nav-link active">My Credit Transfers</Link>
                </li>
                <li className="nav-item">
                    <Link to="/account-info/my-transactions" className="nav-link active">My Transactions</Link>
                </li>
                <li className="nav-item">
                    <Link to="/account-info/my-commissions" className="nav-link disabled">My Commissions</Link>
                </li>

            </ul>
            <Outlet />
        </div>
    )
}

export default AccountInfo;