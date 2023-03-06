import React from 'react'
import { Link, Outlet } from 'react-router-dom'


const Messaging = () => {
    return (
        <div className='pt-3'>
            <ul className="nav nav-tabs pt-4">
                <li className="nav-item">
                    <Link to="/messaging/" className="nav-link active">Send</Link>
                </li>
                <li className="nav-item">
                    <Link to="/messaging/custom" className="nav-link active">Custom</Link>
                </li>

            </ul>
            <Outlet />
        </div>
    )
}

export default Messaging