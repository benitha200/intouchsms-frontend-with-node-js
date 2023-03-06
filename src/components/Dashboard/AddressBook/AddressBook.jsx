import React from 'react'
import { Link, Outlet } from 'react-router-dom'
import Contacts from './Contacts/Contacts'

const AddressBook = () => {
    return (
        <div className='pt-3'>
            <ul className="nav nav-tabs pt-4">
                <li className="nav-item">
                    <Link to="/address-book/" className="nav-link active">Contacts</Link>
                </li>
                <li className="nav-item">
                    <Link to="/address-book/groups" className="nav-link active">Groups</Link>
                </li>

            </ul>
            <Outlet />
        </div>
    )
}

export default AddressBook